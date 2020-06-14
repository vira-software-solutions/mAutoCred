package net.therealvira.dto;

public class Vector3 {
    public static final Vector3 ZERO = new Vector3(0,0,0);
    public static final Vector3 ONE = new Vector3(1,1,1);

    public Vector3(){

    }

    public Vector3(int x, int y, int z){
        this.X = x;
        this.Y = y;
        this.Z = z;
    }

    public int X;
    public int Y;
    public int Z;

    public static final Vector3 Add(Vector3 s1, Vector3 s2){
        return new Vector3(s1.X+s2.X, s1.Y+s2.Y, s1.Z+s2.Z);
    }

    public static final Vector3 Subtract(Vector3 m1, Vector3 s2){
        return new Vector3(m1.X+s2.X, m1.Y+s2.Y, m1.Z+s2.Z);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (Vector3.class.isAssignableFrom(obj.getClass())) {
                final Vector3 dif = (Vector3) obj;
                return dif.X == this.X && dif.Y == this.Y && dif.Z == this.Z;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
}
