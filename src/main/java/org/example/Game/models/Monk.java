package org.example.Game.models;

import java.util.Objects;
import java.util.UUID;
import java.util.Map;
import java.util.List;

public class Monk {
    private UUID id;
    private int x;
    private int y;
    private Map<Integer, List<CultivatedLand>> cultivatedLands;

    public Monk(int x, int y) {
        this.id = UUID.randomUUID();
        this.x = x;
        this.y = y;
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

    public Map<Integer, List<CultivatedLand>> getCultivatedLands() {
        return cultivatedLands;
    }

    public void setCultivatedLands(Map<Integer, List<CultivatedLand>> cultivatedLands) {
        this.cultivatedLands = cultivatedLands;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Monk)) return false;
        Monk monk = (Monk) o;
        return x == monk.x && y == monk.y && id.equals(monk.id) && cultivatedLands.equals(monk.cultivatedLands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, cultivatedLands);
    }

    @Override
    public String toString() {
        return "Monk{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", cultivatedLands=" + cultivatedLands +
                '}';
    }
}

