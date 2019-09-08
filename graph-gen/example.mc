# comment
# not parsed

module RAM ($D_WIDTH, $A_WIDTH) begin
    input data_in[$D_WIDTH]
    input w_addr[$A_WIDTH]
    input r_addr[$A_WIDTH]
    input write_en
    input clk

    output read[$D_WIDTH]

    macro $REGS = $A_WIDTH * $A_WIDTH
    for $x[$A_WIDTH] in $REGS do
        reg data[$D_WIDTH]
        net selected = addr_in = $x
        assign data.clk = write_en & selected & clk
        assign data.in = data_in
        assign read = read | (data.out & selected)
    end
end

module reverse ($WIDTH) begin
    input in[$WIDTH]
    output out[$WIDTH]

    for $x in $WIDTH do
        macro $y = $WIDTH - $x - 1
        assign out[$y] = in[$x]
    end
end

root begin
    module RAM ram(8, 4)
    module reverse rev(4)

    lever addr[4]
    lever data[8]
    button write
    button clk
    lamp display[8]

    assign rev.in = addr

    assign ram.clk = clk

    assign ram.data_in = data
    assign ram.w_addr = rev.out
    assign ram.r_addr = rev.out
    assign display = ram.read
    assign ram.write_en = write
end
