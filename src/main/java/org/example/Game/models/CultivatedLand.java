package org.example.Game.models;

import java.util.Objects;
import java.util.UUID;

public class CultivatedLand {

    private UUID id;
    private Integer x;
    private Integer y;
    private Moves move;

    public CultivatedLand(Integer x, Integer y, Moves move) {
        this.x = x;
        this.y = y;
        this.move = move;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Moves getMove() {
        return move;
    }

    public void setMove(Moves move) {
        this.move = move;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CultivatedLand)) return false;
        CultivatedLand that = (CultivatedLand) o;
        return id.equals(that.id) && x.equals(that.x) && y.equals(that.y) && move == that.move;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, move);
    }

    @Override
    public String toString() {
        return "CultivatedLand{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", move=" + move +
                '}';
    }
}
