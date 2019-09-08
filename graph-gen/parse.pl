#!/usr/bin/env perl

use strict;
use warnings;

my $filename = $ARGV[0];
die "Usage: $0 <codefile.mc>\n" if !$filename;

open(my $fh, '<:encoding(UTF-8)', $filename)
    or die "Could not open file '$filename': $!";


# misc
my $re_ident = qr/[0-9a-zA-Z_-]+/;
my $re_var = qr/\$$re_ident/;
my $re_num = qr/(?:0x|0b)?[0-9]/;

use constant {
    NET_TEMP => 'intermediary',
    NET_INPUT => 'input',
    NET_OUTPUT => 'output',
};

# sections
use constant {
    SECTION_NONE => 'none',
    SECTION_MODULE => 'module',
    SECTION_ROOT => 'root',
};
my $section = SECTION_NONE;
my $current_module;
my $loop_depth = 0;
my $current_for;

# state
my $modules = {};

# helpers
my $info = sub { };
if (defined($ARGV[1]) && $ARGV[1] eq "--verbose") {
    $info = sub {
        my ($text) = @_;
        for my $i (1..$loop_depth) {
            $text = "  " . $text;
        }
        $text = "  " . $text if $section ne SECTION_NONE;
        say STDERR "info: $text";
    }
}

sub module_params_to_vars {
    my ($params) = @_;

    my $retval = {};

    return $retval if !$params;

    $params =~ s/\(|\)//g;
    my @split = split(/\s*,\s*/, $params);
    for my $p (@split) {
        die "invalid module parameter name: $p\n"
            if ! ($p =~ $re_var);
        $info->("parameter: $p");

        $retval->{$p} = {
            param => 1,
        };
    }

    return $retval;
}

sub parse_num {
    my ($str) = @_;
    die "tried to parse invalid number\n"
        if ! ($str =~ $re_num);
    return $str + 0;
}

# main parser loop
$info->("mAutoCred parser reading '$filename'");
$info->("");
my $line = 0;
while (my $row = <$fh>) {
    $line++;
    # clean up and remove comments
    chomp $row;
    $row =~ s/\#.*$//;
    $row =~ s/^\s*|\s$//g;

    # ignore empty lines
    next if $row =~ m/^\s*$/;

    # section handling
    if ($row =~ m/^module\s+($re_ident)\s*(\([^\)]+\))?\s*begin$/) {
        if ($section eq SECTION_NONE) {
            $info->("entered module definition: $1");
            $section = SECTION_MODULE;
            $current_module = $1;

            die "cannot define root as regular module, use 'root' top-level word (line $line)\n"
                if $current_module eq "root";

            die "redefinition of module $current_module on line $line\n"
                if defined($modules->{$current_module});

            $modules->{$current_module} = {
                nets => {},
                regs => {},
                fors => {},
                modules => {},
                ui => {},
                vars => module_params_to_vars($2),
            };

            next;
        }
    } elsif ($row =~ m/^end$/) {
        if ($loop_depth) {
            $loop_depth -= 1;
            $info->("end for loop");
            next;
        }
        $section = SECTION_NONE;
        $info->("end module");
        $info->("");
        next;
    } elsif ($row eq 'root begin') {
        die "root section must be declared top level\n"
            if $section ne SECTION_NONE;
        $info->("entered root module");
        $section = SECTION_ROOT;
        $current_module = "root";
        die "redefinition of root module on line $line\n"
            if defined($modules->{$current_module});

        $modules->{$current_module} = {
            nets => {},
            regs => {},
            fors => {},
            modules => {},
            ui => {},
            vars => module_params_to_vars($2),
        };

        next;
    }

    $row =~ m/^(\S+)\s+(.*)\s*$/;
    my $word = $1;
    my $params = $2;

    my $parse_ident_width = sub {
        my ($pstr, $default) = @_;
        $default //= 1;

        $pstr =~ m/($re_ident)\s*(?:\[($re_var|$re_num)\])?/;
        my $ident = $1;
        my $width = { num => 1 };
        if (defined($2) && $2 ne '') {
            my $match = $2;
            if ($match =~ m/^\$/) {
                die "unknown variable '$match' on line $line\n"
                    if !defined($modules->{$current_module}->{vars}->{$match});
                $width = { var => $match };
            } elsif ($match =~ $re_num) {
                $width = { num => parse_num($match) };
            } else {
                die "invalid net width on line $line: $match\n";
            }
        }

        return ($ident, $width);
    };

    # Add a net definition to the current module scope
    my $define_net = sub {
        my ($type) = @_;

        my ($ident, $width) = $parse_ident_width->($params);

        die "redefinition of net ($type) '$ident' on line $line\n"
            if defined($modules->{$current_module}->{nets}->{$ident});

        $modules->{$current_module}->{nets}->{$ident} = {
            type => $type,
            width => $width,
        };

        if ($loop_depth) {
            $modules->{$current_module}->{nets}->{$ident}->{for} = $current_for;
        }

        my $pr_width = defined($width->{num}) ? $width->{num} : $width->{var};
        $info->("$type '$ident'/$pr_width");
    };

    # 'dynamic dispatch' for words
    my $funcs = {
        "input" => sub {
            $define_net->(NET_INPUT);
        },
        "output" => sub {
            $define_net->(NET_OUTPUT);
        },
        "net" => sub {
            $define_net->(NET_TEMP);
        },

        "macro" => sub {
            $params =~ m/($re_var)\s*=\s*(.*)$/;
            my $set = $1;
            my $expr = $2;

            die "redefinition of variable/macro '$set' on line $line\n"
                if defined($modules->{$current_module}->{vars}->{$set});

            $modules->{$current_module}->{vars}->{$set} = {
                expr => $expr,
            };

            if ($loop_depth) {
                $modules->{$current_module}->{vars}->{$set}->{for} = $current_for;
            }

            $info->("macro setting $set to '$expr'");
        },

        "for" => sub {
            die "nested for not supported yet (line $line)\n"
                if $loop_depth;

            $params =~ m/($re_var)\s+in\s+(.*)\s+do$/;
            my $iterator = $1;
            my $expr = $2;

            die "redefinition of variable/macro '$iterator' on line $line\n"
                if defined($modules->{$current_module}->{vars}->{$iterator});
            die "redefinition of iterator '$iterator' on line $line\n"
                if defined($modules->{$current_module}->{fors}->{$iterator});

            $modules->{$current_module}->{fors}->{$iterator} = {
                expr => $expr,
            };
            $current_for = $iterator;

            $info->("begin for loop ($iterator over '$expr')");
            $loop_depth++;
        },

        "reg" => sub {
            my ($ident, $width) = $parse_ident_width->($params);

            die "redefinition of reg '$ident' on line $line\n"
                if defined($modules->{$current_module}->{regs}->{$ident});

            $modules->{$current_module}->{regs}->{$ident} = {
                width => $width,
            };

            if ($loop_depth) {
                $modules->{$current_module}->{regs}->{$ident}->{for} = $current_for;
            }

            my $pr_width = defined($width->{num}) ? $width->{num} : $width->{var};
            $info->("register '$ident'/$pr_width");
        },

        "assign" => sub {
            $params =~ m/^(.*?)\s*=\s*(.*)$/;

            my ($set_ident, $set_index) = $parse_ident_width->($1, 0);


        },

        "module" => sub {
        },

        "lever" => sub {
        },
        "button" => sub {
        },
        "lamp" => sub {
        }
    };

    if (defined($funcs->{$word})) {
        $funcs->{$word}->();
        next;
    }

    die "syntax error on line $line: Invalid word '$word'\n";
}

use Data::Dumper;
#print Dumper($modules);

$info->("parsing complete!");
