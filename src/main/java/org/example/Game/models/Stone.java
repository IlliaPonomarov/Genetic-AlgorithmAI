package org.example.Game.models;

import java.util.Objects;
import java.util.UUID;

public class Stone{
    private UUID id;
    private int rowPos;
    private int columnPos;
    private int size;

    private int turn;

    public Stone(int rowPos, int columnPos,  int size, int turn) {
        this.id = UUID.randomUUID();
        this.rowPos = rowPos;
        this.columnPos = columnPos;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stone)) return false;
        Stone stone = (Stone) o;
        return rowPos == stone.rowPos && columnPos == stone.columnPos && size == stone.size && turn == stone.turn && Objects.equals(id, stone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rowPos, columnPos, size, turn);
    }

    @Override
    public String toString() {
        return "Stone{" +
                "id=" + id +
                ", rowPos=" + rowPos +
                ", columnPos=" + columnPos +
                ", size=" + size +
                ", turn=" + turn +
                '}';
    }
}
