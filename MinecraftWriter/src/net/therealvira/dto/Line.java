package net.therealvira.dto;

import net.therealvira.minecraft.gates.Direction;

public class Line {
    private Vector3 position;
    private int length;
    private Direction direction;

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
