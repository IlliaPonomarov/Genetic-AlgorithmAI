package org.example;

import org.example.Game.models.Rake;
import org.example.Game.models.ZenGarden;

import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {


    private Integer countOfPopulation;
    private Integer maxGenes;
    private Map<List<Integer>, Integer> populations;
    private Map<List<Integer>, Integer> newPopulations;
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

        if (chromosomes.size() > 0)
            for (int i = 0; i < chromosomes.size(); i++)
                while (chromosomes.get(i).equals(randomGene)) {
                    randomGene = random.nextInt(maxBorder);
                    i = 0;
                }

        return randomGene;
    }

    public void initPopulation() {
        int fitness = 0;
        List<Integer> chromosomes = new ArrayList<>();
        this.populations = new HashMap<>();

        for (int i = 0; i < countOfPopulation; i++) {
            for (int j = 0; j < maxGenes; j++)
                chromosomes.add(randomGene(chromosomes));


            Rake rake = new Rake(fitness, this.zenGarden);
            fitness = rake.rake(Collections.singletonMap(chromosomes, 0));
            this.populations.put(chromosomes, fitness);

            chromosomes = new ArrayList<>();
            fitness = 0;

        }

        this.populations = sortChromosomesByFitness();

    }

    public void roulette() {

    }

    private Optional<Map<List<Integer>, Integer>> getChromosomesAndFitnessByIndex(int index) {
        List<Integer> chromosomes = (List<Integer>) this.populations.keySet().toArray()[index];
        int fitness = this.populations.get(chromosomes);
        return Optional.of(Collections.singletonMap(chromosomes, fitness));
    }
    private Optional<List<Integer>> getChromosomesByIndex(int index) {
        List<Integer> chromosomes = (List<Integer>) this.populations.keySet().toArray()[index];
        return Optional.of(chromosomes);
    }
    private Optional<Integer> getFitnessByIndex(int index) {
        List<Integer> chromosomes = (List<Integer>) this.populations.keySet().toArray()[index];
        int fitness = this.populations.get(chromosomes);
        return Optional.of(fitness);
    }
    public Optional<Map<List<Integer>, Integer>> resolve() {

        int totalFitness = 0;
        int emptyCellInZenGarden = this.zenGarden.getWidth() * this.zenGarden.getHeight() - this.zenGarden.getStones().size();

        if (getChromosomesAndFitnessByIndex(0).isEmpty())
            return Optional.empty();

        int dominantFitness = getFitnessByIndex(0).get();
        List<Integer> dominantChromosomes = getChromosomesByIndex(0).get();
        List<Integer> allFitness = new ArrayList<>(this.populations.values());

        if (dominantFitness ==  emptyCellInZenGarden)
            return Optional.of(Collections.singletonMap(dominantChromosomes, dominantFitness));


        totalFitness = allFitness.stream().reduce(0, Integer::sum);
        System.out.printf("Worst Fitness %d\n", getFitnessByIndex(this.populations.size() - 1).get());
        System.out.printf("Best Fitness %d\n", getFitnessByIndex(0).get());
        System.out.printf("Average Fitness %d\n", totalFitness / this.populations.size());


        if (elitizmus().isEmpty())
            throw new IllegalArgumentException();

        // Elitizmus
        this.newPopulations = elitizmus().get();


        // Replace better fitness to worst
        this.populations = replaceWorstFitnessToRandomValue();

        while (this.newPopulations.size() < 100) {
            Map<List<Integer>, Integer> first_chromosome = null;
            Map<List<Integer>, Integer> second_chromosome = null;
        }



        return Optional.empty();
    }


    public Map<List<Integer>, Integer> tournament() {

    }




    public Map<List<Integer>, Integer> replaceWorstFitnessToRandomValue() {
        Map<List<Integer>, Integer>  updatedCurrentPopulation = new LinkedHashMap<>();
        List<List<Integer>> chromosomes = new ArrayList<>(this.populations.keySet());
        List<Integer> fitnesses = new ArrayList<>(this.populations.values());

        IntStream.range(0, this.populations.size() / 2).forEach(i -> {
            int lastIndex = this.populations.size() - i - 1;
            List<Integer> worstChromosome = chromosomes.get(lastIndex);

            IntStream.range(0, worstChromosome.size()).forEach(j -> {
                worstChromosome.set(j, randomGene(worstChromosome));
            });
            chromosomes.set(lastIndex, worstChromosome);

        });

        IntStream.range(0, chromosomes.size()).forEach(i -> updatedCurrentPopulation.put(chromosomes.get(i), fitnesses.get(i)));

        return updatedCurrentPopulation;
    }

    public Optional<Map<List<Integer>, Integer>> elitizmus() {
        int i = 0;
        int elitizmus = (int) (this.populations.size() * 0.02);
        Map<List<Integer>,Integer> newPopulation = new LinkedHashMap<>();
        IntStream.range(0, elitizmus).forEach( index -> newPopulation.put(getChromosomesByIndex(index).get(), getFitnessByIndex(index).get()));
        return Optional.of(newPopulation);
    }

    public Map<List<Integer>, Integer> sortChromosomesByFitness(){
        return this.populations.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Integer getCountOfPopulation() {
        return countOfPopulation;
    }

    public void setCountOfPopulation(Integer countOfPopulation) {
        this.countOfPopulation = countOfPopulation;
    }

    public Integer getMaxGenes() {
        return maxGenes;
    }

    public void setMaxGenes(Integer maxGenes) {
        this.maxGenes = maxGenes;
    }

    public Map<List<Integer>, Integer> getPopulations() {
        return populations;
    }

    public void setPopulations(Map<List<Integer>, Integer> populations) {
        this.populations = populations;
    }

    public Map<List<Integer>, Integer> getNewPopulations() {
        return newPopulations;
    }

    public void setNewPopulations(Map<List<Integer>, Integer> newPopulations) {
        this.newPopulations = newPopulations;
    }

    public Integer getMaxBorder() {
        return maxBorder;
    }

    public void setMaxBorder(Integer maxBorder) {
        this.maxBorder = maxBorder;
    }

    public ZenGarden getZenGarden() {
        return zenGarden;
    }

    public void setZenGarden(ZenGarden zenGarden) {
        this.zenGarden = zenGarden;
    }
}
