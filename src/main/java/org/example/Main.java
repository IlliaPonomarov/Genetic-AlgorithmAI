package org.example;

import org.example.Game.models.ZenGarden;
import org.example.Game.services.ZenGardenService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        int generation;
        int sizeOfPopulation = 0;
        int countOfGeneration = 0;
        int width = 0;
        int height = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter size of population: ");
        sizeOfPopulation = scanner.nextInt();
        System.out.println("Enter count of generation: ");
        countOfGeneration = scanner.nextInt();
        System.out.println("Width of Zen Garden");
        width = scanner.nextInt();
        System.out.println("Height of Zen Garden");
        height = scanner.nextInt();

        Settings settings = new Settings(countOfGeneration, sizeOfPopulation);
        // Init zen garden without the monk
        ZenGardenService zenGardenService = new ZenGardenService();
        ZenGarden zenGarden = zenGardenService.generateStartGarden(width, height);
        Optional<Map<List<Integer>, Integer>> result = Optional.empty();
        int countOfStones = 6;

        // Get max count of genes into chromosome
        int maxGenes = ( height + width + countOfStones ) / 2;

        // Max Monk position in zen garden
        int maxBorder = ( ( height + width ) * 2 ) - 5;
        Population firstPopulation = new Population(settings.getCountOfPopulation(), maxGenes, maxBorder, zenGarden, settings);

        System.out.println("Start game field:");
        zenGarden.getMatrixShow().printMatrix();

        // Init first population
        firstPopulation.initPopulation();


        // Looking for best solution ...
        for (generation = 0; generation < settings.getGeneration(); generation++) {

            result = firstPopulation.resolve();

            if (result.isPresent()){
                statistics();
                System.out.printf("Best Solution %d", generation);
                // print best solution
                System.out.println();
                firstPopulation.printBestSolution();
                break;
            }
        }

        if (generation == settings.getGeneration() && result.isEmpty()) {
            statistics();
            System.out.println("Better solution for this game :)");
            firstPopulation.printBestSolution();
        }
    }

    public static void statistics() {
        System.out.println();
        System.out.println("Average Best fitness: " + Global.globalBetterFitness.stream().mapToInt(i -> i).sum() / Global.globalBetterFitness.size());
        System.out.println("Average Worst fitness: " + Global.globalWorstFitness.stream().mapToInt(i -> i).sum() / Global.globalWorstFitness.size());
    }


}
