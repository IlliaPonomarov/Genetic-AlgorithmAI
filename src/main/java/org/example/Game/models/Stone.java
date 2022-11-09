package org.example.Game.models;

import java.util.Objects;
import java.util.UUID;

public class Stone{
    private UUID id;
    private int rowPos;
    private int columnPos;

    private Turns turn;

    public Stone(int rowPos, int columnPos, Turns turn) {
        this.id = UUID.randomUUID();
        this.rowPos = rowPos;
        this.columnPos = columnPos;
        this.turn = turn;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getRowPos() {
        return rowPos;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    public int getColumnPos() {
        return columnPos;
    }

    public void setColumnPos(int columnPos) {
        this.columnPos = columnPos;
    }




    public Turns getTurn() {
        return turn;
    }

    public void setTurn(Turns turn) {
        this.turn = turn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stone)) return false;
        Stone stone = (Stone) o;
        return rowPos == stone.rowPos && columnPos == stone.columnPos && id.equals(stone.id) && turn == stone.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rowPos, columnPos,  turn);
    }

    @Override
    public String toString() {
        return "Stone{" +
                "id=" + id +
                ", rowPos=" + rowPos +
                ", columnPos=" + columnPos +
                ", turn=" + turn +
                '}';
    }
}
