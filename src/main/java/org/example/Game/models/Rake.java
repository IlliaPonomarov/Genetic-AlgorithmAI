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

    public int rake(Map<List<Integer>, Integer> chromosomes) {
        ZenGarden cloneGarden = this.zenGarden.clone();
        int cultivatedRake = 1;
        int column = this.zenGarden.getWidth();
        int row = this.zenGarden.getHeight();


        for (Map.Entry<List<Integer>, Integer> chromosomeFitness : chromosomes.entrySet()) {
            List<Integer> currentGenes = chromosomeFitness.getKey();

            for (Integer gene : currentGenes) {


                 if (gene < column) {
                     System.out.println("Gene start on top: " + gene);
                     int currentRow = 0;
                     int currentColumn = gene;
                     fitness = generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.BOTTOM);
                 }

                 else if (gene < ((column + row) - 2)) {
                     System.out.println("Gene start on right: " + gene);
                     int currentRow = gene - column + 1;
                     int currentColumn = column - 1;
                     fitness = generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.LEFT);

                 }

                 else if (gene < (2 * (column) + row) - 2) {
                     System.out.println("Gene start on down: " + gene);
                     int currentRow = row - 1;
                     int currentColumn = (column * 2 + row - 3) - gene;
                     fitness = generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.TOP);
                 }
                 else {
                     System.out.println("Gene start on left: " + gene);
                     int currentRow = column * 2 + row * 2 - 4 - gene;
                     int currentColumn = 0;
                     fitness = generateFitness(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.RIGHT);
                 }


            }
        }
        return 0;
    }

    public void cultivateGarden(MatrixShow matrixShow, Integer[][] oldMatrix, int cultivatedGarden, int row, int column) {
        oldMatrix[row][column] = cultivatedGarden;
    }

    public int generateFitness(int row, int column, int order, ZenGarden cloneGarden, int path, Moves moves) {
        int cultivatedGarden = 1;
        MatrixShow currentGameState = cloneGarden.getMatrixShow();
        Integer[][] currentMatrix= currentGameState.getMatrix();

        if (currentMatrix[row][column].equals(0)){
            while (true) {
                currentMatrix[row][column] = cultivatedGarden;
                currentGameState.setMatrix(currentMatrix);

                if (moves.equals(Moves.TOP)) {



                }


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
