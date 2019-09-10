package net.therealvira.minecraft.gates;

public class IOcomConnection {
    public IOcomConnection(){}

    public IOcomConnection(Gate I, int Icom, Gate O, int Ocom){
        this.I = I;
        this.O = O;

        this.Icom = Icom;
        this.Ocom = Ocom;
    }

    Gate I;
    int Icom;

    Gate O;
    int Ocom;
}
