package net.therealvira.dto;

import net.therealvira.minecraft.gates.Direction;

public class Gate {
    private String type;
    private Direction direction;
    private Vector3 position;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }
}
