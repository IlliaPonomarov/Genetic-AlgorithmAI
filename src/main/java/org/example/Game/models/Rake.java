package org.example.Game.models;
import org.example.Game.MatrixShow;

import java.util.*;

public class Rake {

    private ZenGarden zenGarden;

    public Rake(ZenGarden zenGarden) {
        this.zenGarden = zenGarden;
    }

    public int rake(Map<List<Integer>, Integer> chromosomes) {
        ZenGarden cloneGarden = this.zenGarden.clone();
        int cultivatedRake = 1;
        int column = this.zenGarden.getWidth();
        int row = this.zenGarden.getHeight();
        int fitness = 0;


        // Get genes and fitness of chromosome
        for (Map.Entry<List<Integer>, Integer> chromosomeFitness : chromosomes.entrySet()) {
            List<Integer> currentGenes = chromosomeFitness.getKey();

            for (Integer gene : currentGenes) {

                // If Monk was spawned on top
                 if (gene < column) {
                     int currentRow = 0;
                     int currentColumn = gene;
                     fitness += generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.TOP);
                 }
                 // If Monk was spawned on right
                 else if (gene < ((column + row) - 2)) {
                     int currentRow = gene - column + 1;
                     int currentColumn = column - 1;
                     fitness += generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.RIGHT);
                 }
                 // If Monk was spawned on down
                 else if (gene < (2 * (column) + row) - 2) {
                     int currentRow = row - 1;
                     int currentColumn = (column * 2 + row - 3) - gene;
                     fitness += generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.BOTTOM);
                 }
                 // If Monk was spawned on left
                 else {
                     int currentRow = column * 2 + row * 2 - 4 - gene;
                     int currentColumn = 0;
                     fitness += generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.LEFT);

                 }
                 cultivatedRake++;

            }
        }

        return fitness;
    }

    // Update uncultivated to cultivated cell
    public void cultivateGarden(MatrixShow matrixShow, Integer[][] oldMatrix, int order, int row, int column) {
        oldMatrix[row][column] = order;
        matrixShow.setMatrix(oldMatrix);
    }

    // We get current fitness of cultivated cell by current order
    public int generateFitness(int row, int column, int order, ZenGarden cloneGarden, int path, Moves moves) {
        int cultivatedGarden = 1;
        MatrixShow currentGameState = cloneGarden.getMatrixShow();
        Integer[][] currentMatrix= currentGameState.getMatrix();

        if (currentMatrix[row][column].equals(0)) {
            while (true) {
                boolean top = currentGameState.moveTop(row, column);
                boolean left = currentGameState.moveLeft(row, column);
                boolean right = currentGameState.moveRight(row, column);
                boolean bottom = currentGameState.moveBottom(row, column);
                cultivateGarden(currentGameState, currentMatrix, order, row, column);


                //  REFACTOR
                switch (moves) {
                    // Move our monk to down
                    case TOP:
                        if (bottom) {
                            cultivatedGarden++;
                            row++;
                        }
                        // If We now on the board of zen garden then we return current count of cultivated cell on the zen garden
                        else if (currentGameState.border(row, column, moves))
                            return cultivatedGarden;
                        // Move our monk to right (If we can't move to top)
                        else if (right)
                            moves = Moves.LEFT;
                        // Move our monk to down, (If we can't move to top , however we can move to down)
                        else if (left)
                            moves = Moves.RIGHT;
                        else
                            return 0;
                        break;
                    case BOTTOM:
                        // Move our monk to top (If we can move)
                        if (top) {
                            cultivatedGarden++;
                            row--;
                        }
                        // If We now on the board of zen garden then we return current count of cultivated cell on the zen garden
                        else if (currentGameState.border(row, column, moves))
                            return cultivatedGarden;
                        else if (right)
                            moves = Moves.LEFT;
                        else if (left)
                            moves = Moves.RIGHT;
                        else return 0;
                        break;
                    case LEFT:
                        // Move our monk to right (If we can move)
                        if (right) {
                            cultivatedGarden++;
                            column++;
                        }
                        // If We now on the board of zen garden then we return current count of cultivated cell on the zen garden
                        else if (currentGameState.border(row, column, moves))
                            return cultivatedGarden;
                        // Move our monk to down (If we can't move to right , however we can move to down)
                        else if (top)
                            moves = Moves.BOTTOM;
                        // Move our monk to top (If we can't move to right and down , however we can move to top)
                        else if (bottom)
                            moves = Moves.TOP;
                        else return 0;

                        break;
                    case RIGHT:
                        // Move our monk to left (If we can move)
                        if (left) {
                            column--;
                            cultivatedGarden++;
                        }
                        // If We now on the board of zen garden then we return current count of cultivated cell on the zen garden
                        else if (currentGameState.border(row, column, moves))
                            return cultivatedGarden;

                        // Move our monk to down (If we can't move to left , however we can move to down)
                        else if (top)
                            moves = Moves.BOTTOM;
                        // Move our monk to down (If we can't move to left and down , however we can move to top)
                        else if (bottom)
                            moves = Moves.TOP;
                        else return 0;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        return 0;
    }

    public ZenGarden getZenGarden() {
        return zenGarden;
    }

    public void setZenGarden(ZenGarden zenGarden) {
        this.zenGarden = zenGarden;
    }

    @Override
    public String toString() {
        return "Rake{" +
                ", zenGarden=" + zenGarden +
                '}';
    }
}
