package org.example;

import org.example.Game.models.ZenGarden;
import org.example.Game.services.ZenGardenService;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        int generation;
        boolean solution = false;

        Settings settings = new Settings(1000, 100);
        // Init zen garden without the monk
        ZenGardenService zenGardenService = new ZenGardenService();
        ZenGarden zenGarden = zenGardenService.generateStartGarden();
        Optional<Map<List<Integer>, Integer>> result;
        int height = zenGarden.getHeight();
        int width = zenGarden.getWidth();
        //int countOfStones = zenGarden.getStones().size();
        int countOfStones = 6;


        // 2 * (lines + columns) - 4

        int maxGenes = ( height + width + countOfStones ) / 2;
        int maxBorder = ( ( height + width ) * 2 ) - 5;
        Population firstPopulation = new Population(settings.getCountOfPopulation(), maxGenes, maxBorder,zenGarden);
        zenGarden.getMatrixShow().printMatrix();
        firstPopulation.initPopulation();



        for (generation = 0; generation < settings.getGeneration(); generation++) {

            result = firstPopulation.resolve();

            if (result.isPresent()){
                System.out.printf("Best Solution %d", generation);
                // print best solution

                break;
            }
        }

        if (generation == settings.getCountOfPopulation() && !solution) {
            System.out.println("Program didn't find best solution for this game");
        }


        // Add monk to zen garden ( random position )
        zenGarden.setMonk(zenGardenService.addMonkToRandomPosition(zenGarden));

        // Print first state
        zenGarden.getMatrixShow().printMatrix();

        System.out.println(zenGarden.toString());

    }
}
