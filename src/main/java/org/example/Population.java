package org.example;

import org.example.Game.models.Monk;
import org.example.Game.models.Rake;
import org.example.Game.models.ZenGarden;

import java.util.Map;
import java.util.*;

public class Population {


    private Integer countOfPopulation;
    private Integer maxGenes;
    private Map<List<Integer>, Integer> chromosomeMap;
    private Integer maxBorder;
    private ZenGarden zenGarden;

    public Population(Integer countOfPopulation, Integer maxGenes, Integer maxBorder, ZenGarden zenGarden) {
        this.countOfPopulation = countOfPopulation;
        this.maxGenes = maxGenes;
        this.maxBorder = maxBorder;
        this.zenGarden = zenGarden;
    }

    public Integer randomGene(List<Integer> chromosomes) {

        Random random  = new Random();
        int randomGene = random.nextInt(maxBorder);

        if (chromosomes.size() > 0) {
            for (Integer gene : chromosomes)
                while (gene.equals(randomGene)) randomGene = random.nextInt(maxBorder);
        }

        return randomGene;
    }

    public void initPopulation() {
        int fitness = 0;
        List<Integer> chromosomes = new ArrayList<>();
        this.chromosomeMap = new HashMap<>();

        for (int i = 0; i < countOfPopulation; i++) {
            this.chromosomeMap.put(chromosomes, fitness);

            for (int j = 0; j < maxGenes; j++)
                chromosomes.add(randomGene(chromosomes));


            Rake rake = new Rake(fitness, this.zenGarden);
            fitness = rake.rake(this.chromosomeMap);
            this.chromosomeMap.put(chromosomes, fitness);
            chromosomes = new ArrayList<>();
            fitness = 0;

        }
    }

}
