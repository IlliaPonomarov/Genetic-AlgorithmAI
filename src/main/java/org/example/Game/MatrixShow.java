package org.example.Game;

import org.example.Game.models.Moves;
import org.example.Game.models.Stone;
import org.example.Game.models.ZenGarden;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class MatrixShow {

    private UUID id;
    private int column;
    private int row;
    private Integer[][] matrix;
    private ZenGarden zenGarden;

    public MatrixShow(ZenGarden zenGarden) {
        this.id = UUID.randomUUID();
        this.column = zenGarden.getWidth();
        this.row = zenGarden.getHeight();
        this.zenGarden = zenGarden;
        this.matrix = new Integer[row][column];


        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                this.matrix[i][j] = 0;

        zenGarden.getStones().forEach(this::addStoneToMatrix);
    }

    private void addStoneToMatrix(Stone stone) {
        int row = stone.getRowPos();
        int column = stone.getColumnPos();
        this.matrix[row][column] = -1;
    }


    public void printMatrix() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(this.matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public boolean moveRight(int row, int column) {
        int moveRight = column + 1;

        return moveRight < this.column && !this.matrix[row][moveRight].equals(-1);
    }

    public boolean moveLeft(int row, int column) {
        int moveLeft = column - 1;

        return moveLeft  > 0 && !this.matrix[row][moveLeft].equals(-1);
    }

    public boolean moveTop(int row, int column) {
        int moveTop = row - 1;

        return row > 0 && !this.matrix[moveTop][column].equals(-1);
    }

    public boolean moveBottom(int row, int column) {
        int moveBottom = row + 1;

        return moveBottom < row && !this.matrix[moveBottom][column].equals(-1);
    }

    public boolean border(int row, int column, Moves move) {
       return move.equals(Moves.RIGHT) && column == this.column ||
               move.equals(Moves.LEFT) && column == 0 ||
               move.equals(Moves.BOTTOM) && row == this.row - 1 ||
               move.equals(Moves.TOP)  && row == 0;
    }





    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Integer[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Integer[][] matrix) {
        this.matrix = matrix;
    }

    public ZenGarden getZenGarden() {
        return zenGarden;
    }

    public void setZenGarden(ZenGarden zenGarden) {
        this.zenGarden = zenGarden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatrixShow)) return false;
        MatrixShow that = (MatrixShow) o;
        return column == that.column && row == that.row && Objects.equals(id, that.id) && Arrays.equals(matrix, that.matrix) && Objects.equals(zenGarden, that.zenGarden);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, column, row, zenGarden);
        result = 31 * result + Arrays.hashCode(matrix);
        return result;
    }

    @Override
    public String toString() {
        return "MatrixShow{" +
                "id=" + id +
                ", width=" + column +
                ", height=" + row +
                ", matrix=" + Arrays.toString(matrix) +
                ", zenGarden=" + zenGarden +
                '}';
    }
}
