package org.example.Game.models;
import org.example.Game.MatrixShow;

import java.util.*;

public class Rake {


    private int fitness;
    private ZenGarden zenGarden;

    public Rake(int fitness, ZenGarden zenGarden) {
        this.fitness = fitness;
        this.zenGarden = zenGarden;
    }


    public int fitnessCheck(int row, int column, ZenGarden zenGarden, int order, Moves move, int fitness) {

        return -1;
    }

    public int rake(Map<List<Integer>, Integer> chromosomes) {
        ZenGarden cloneGarden = this.zenGarden.clone();
        int cultivatedRake = 1;
        int column = this.zenGarden.getWidth();
        int row = this.zenGarden.getHeight();
        int fitness = 0;


        for (Map.Entry<List<Integer>, Integer> chromosomeFitness : chromosomes.entrySet()) {
            List<Integer> currentGenes = chromosomeFitness.getKey();

            for (Integer gene : currentGenes) {


                 if (gene < column) {
                     int currentRow = 0;
                     int currentColumn = gene;
                     fitness += generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.TOP);
                 }

                 else if (gene < ((column + row) - 2)) {
                     int currentRow = gene - column + 1;
                     int currentColumn = column - 1;
                     fitness += generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.RIGHT);
                 }

                 else if (gene < (2 * (column) + row) - 2) {
                     int currentRow = row - 1;
                     int currentColumn = (column * 2 + row - 3) - gene;
                     fitness += generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.BOTTOM);
                 }
                 else {
                     System.out.println("Gene start on left: " + gene);
                     int currentRow = column * 2 + row * 2 - 4 - gene;
                     int currentColumn = 0;
                     fitness += generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.LEFT);

                 }

                // cloneGarden.getMatrixShow().printMatrix();
                 cultivatedRake++;

            }
        }

        return fitness;
    }

    public void cultivateGarden(MatrixShow matrixShow, Integer[][] oldMatrix, int order, int row, int column) {
        oldMatrix[row][column] = order;
        matrixShow.setMatrix(oldMatrix);
//        matrixShow.printMatrix();
//        System.out.println();

    }

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
                    case TOP:
                        if (bottom) {
                            cultivatedGarden++;
                            row++;
                        }
                        else if (currentGameState.border(row, column, moves))
                            return cultivatedGarden;
                        else if (right)
                            moves = Moves.LEFT;
                        else if (left)
                            moves = Moves.RIGHT;
                        else
                            return 0;
                        break;
                    case BOTTOM:
                        if (top) {
                            cultivatedGarden++;
                            row--;
                        } else if (currentGameState.border(row, column, moves))
                            return cultivatedGarden;
                        else if (right)
                            moves = Moves.LEFT;
                        else if (left)
                            moves = Moves.RIGHT;
                        else return 0;
                        break;
                    case LEFT:
                        if (right) {
                            cultivatedGarden++;
                            column++;
                        } else if (currentGameState.border(row, column, moves))
                            return cultivatedGarden;
                        else if (top)
                            moves = Moves.BOTTOM;
                        else if (bottom)
                            moves = Moves.TOP;
                        else return 0;

                        break;
                    case RIGHT:
                        if (left) {
                            column--;
                            cultivatedGarden++;
                        } else if (currentGameState.border(row, column, moves))
                            return cultivatedGarden;
                        else if (top) moves = Moves.BOTTOM;
                        else if (bottom) moves = Moves.TOP;
                        else return 0;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }




//                if (moves.equals(Moves.TOP)) {
//                    if (bottom) {
//                        cultivatedGarden++;
//                        row--;
//                    }
//                    else if (border)
//                        return fitness;
//
//                    else if (right)
//                        moves = Moves.RIGHT;
//
//                    else if (left)
//                        moves = Moves.LEFT;
//                    else return 0;
//
//                }
//                else if (moves.equals(Moves.BOTTOM)) {
//                    if (top) {
//                        cultivatedGarden++;
//                        row++;
//                    }
//                    else if (border)
//                        return fitness;
//                    else if (right)
//                        moves = Moves.RIGHT;
//                    else if (left)
//                        moves = Moves.LEFT;
//                    else return 0;
//                }
//                else if (moves.equals(Moves.LEFT)) {
//                    if (left) {
//                        cultivatedGarden++;
//                        column--;
//                    } else if (border)
//                        return fitness;
//                    else if (top)
//                        moves = Moves.TOP;
//                    else if (bottom)
//                        moves = Moves.BOTTOM;
//                    else return 0;
//
//
//                }
//                else if (moves.equals(Moves.RIGHT)) {
//                    if (right) {
//                        column++;
//                        cultivatedGarden++;
//                    }
//                    else if (border)
//                        return fitness;
//                    else if (top) moves = Moves.TOP;
//                    else if (bottom) moves = Moves.BOTTOM;
//                    else return 0;
//
//
//                }
//
//            }
            }
        }


        System.out.printf("%d %d\n", row, column);

        return 0;
    }


    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
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
                "fitness=" + fitness +
                ", zenGarden=" + zenGarden +
                '}';
    }
}
