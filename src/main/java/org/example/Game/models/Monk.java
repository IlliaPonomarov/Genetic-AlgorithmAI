package org.example.Game.models;

import java.util.Objects;
import java.util.UUID;

public class Monk {
    private UUID id;
    private int x;
    private int y;
    private char position;

    public Monk(int x, int y, char position){
        this.id = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.position = position;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getPosition() {
        return position;
    }

    public void setPosition(char position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Monk)) return false;
        Monk monk = (Monk) o;
        return x == monk.x && y == monk.y && position == monk.position && Objects.equals(id, monk.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, position);
    }

    @Override
    public String toString() {
        return "Monk{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", position=" + position +
                '}';
    }
}
