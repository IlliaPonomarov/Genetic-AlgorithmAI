package org.example;

import org.example.Game.MatrixShow;
import org.example.Game.models.Moves;
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
    private Settings settings;

    public Population(Integer countOfPopulation, Integer maxGenes, Integer maxBorder, ZenGarden zenGarden, Settings settings) {
        this.countOfPopulation = countOfPopulation;
        this.maxGenes = maxGenes;
        this.maxBorder = maxBorder;
        this.zenGarden = zenGarden;
        this.settings = settings;
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

        // Generating of random genes for chromosomes
        for (int i = 0; i < countOfPopulation; i++) {
            for (int j = 0; j < maxGenes; j++)
                chromosomes.add(randomGene(chromosomes));

            // init our monk rake :)
            Rake rake = new Rake(this.zenGarden);

            // Calculating our fitness for each chromosome
            fitness = rake.rake(Collections.singletonMap(chromosomes, 0));
            this.populations.put(chromosomes, fitness);

            chromosomes = new ArrayList<>();
            fitness = 0;
            this.populations = sortChromosomesByFitnessDesc();

        }

    }

    private Optional<Map<List<Integer>, Integer>> getChromosomesAndFitnessByIndex(int index, Map<List<Integer>, Integer> map) {
        List<Integer> chromosomes = (List<Integer>) map.keySet().toArray()[index];
        int fitness = map.get(chromosomes);
        return Optional.of(Collections.singletonMap(chromosomes, fitness));
    }
    private Optional<List<Integer>> getChromosomesByIndex(int index, Map<List<Integer>, Integer> map) {
        List<Integer> chromosomes = (List<Integer>) map.keySet().toArray()[index];
        return Optional.of(chromosomes);
    }
    private Optional<Integer> getFitnessByIndex(int index, Map<List<Integer>, Integer> map) {
        List<Integer> chromosomes = (List<Integer>) map.keySet().toArray()[index];
        int fitness = map.get(chromosomes);
        return Optional.of(fitness);
    }
    public Optional<Map<List<Integer>, Integer>> resolve() {

        this.populations = sortChromosomesByFitnessDesc();
        int totalFitness = 0;

        if (getChromosomesAndFitnessByIndex(0, this.populations).isEmpty())
            return Optional.empty();

        // We get dominant chromosome and fitness from population.
        int dominantFitness = getFitnessByIndex(0, this.populations).get();
        List<Integer> dominantChromosomes = getChromosomesByIndex(0, this.populations).get();

        // If everything cells were cultivated then return of success zen garden state.
        if (dominantFitness ==  this.zenGarden.emptyCellInZenGarden())
            return Optional.of(Collections.singletonMap(dominantChromosomes, dominantFitness));

        totalFitness = this.populations.values().stream().mapToInt(i -> i).sum();

        if (eletarism().isEmpty())
            throw new IllegalArgumentException();

        // Select chromosomes with support to eletarism
        this.newPopulations = eletarism().get();

        // Replace the worst fitness to random value
        this.populations = replaceWorstFitnessToRandomValue();

        // Generating of new population
        while (this.newPopulations.size() < settings.getCountOfPopulation()) {

            // Get 2 chromosomes with support of tournament
            Map<List<Integer>, Integer> firstChromosome = tournament();
            Map<List<Integer>, Integer> secondChromosome = tournament();

            // crossover to chromosomes
            this.newPopulations.put(crossoverDominantChromosome(firstChromosome, secondChromosome), 0);

            // Get 2 chromosomes with support of roulette
            firstChromosome = roulette(totalFitness);
            secondChromosome = roulette(totalFitness);

            this.newPopulations.put(crossoverDominantChromosome(firstChromosome, secondChromosome), 0);
        }

        this.populations = new LinkedHashMap<>();

        // Generating fitness for chromosomes for new population
        for (Map.Entry<List<Integer>, Integer> newChromosome : this.newPopulations.entrySet()){
            int newFitness = 0;
            if (newChromosome.getValue() == newFitness){
                Rake rake = new Rake(this.zenGarden);
                newFitness = rake.rake(Collections.singletonMap(newChromosome.getKey(), newFitness));
            }
            this.populations.put(newChromosome.getKey(),newFitness);
        }


        return Optional.empty();
    }

    public List<Integer> crossoverDominantChromosome(Map<List<Integer>, Integer> first, Map<List<Integer>, Integer> second){
        int firstFitness = getFitnessByIndex(0, first).get();
        int secondFitness = getFitnessByIndex(0, second).get();

        if (firstFitness > secondFitness)
            return crossover(first, second);

        return crossover(second, first);
    }

    public List<Integer> crossover(Map<List<Integer>, Integer> dominant, Map<List<Integer>, Integer> weakest) {
        List<Integer> childChromosomes = new ArrayList<>();
        Random random = new Random();
        List<Integer> dominantChromosomes = getChromosomesByIndex(0, dominant).get();
        List<Integer> weakestChromosomes = getChromosomesByIndex(0, weakest).get();

        int ratio = random.nextInt(dominantChromosomes.size() - 1);

        if (ratio == 0)
            ratio = 1;

        IntStream.range(0, ratio).forEach(i -> childChromosomes.add(dominantChromosomes.get(i)));
        IntStream.range(ratio, weakestChromosomes.size()).forEach(i -> childChromosomes.add(weakestChromosomes.get(i)));

        // Mutations
        for (int i = 0; i < childChromosomes.size(); i++)
            if (random.nextInt(1000) <= 20)
                childChromosomes.set(i, randomGene(childChromosomes));


        return childChromosomes;
    }

    public Map<List<Integer>, Integer> tournament() {
        int randomFirstChromosome = new Random().nextInt(this.populations.size() - 1);
        int randomSecondChromosome = new Random().nextInt(this.populations.size() - 1);

        while (randomFirstChromosome == randomSecondChromosome) randomSecondChromosome = new Random().nextInt(this.populations.size() - 1);

        int firstFitness = getFitnessByIndex(randomFirstChromosome, this.populations).get();
        int secondFitness = getFitnessByIndex(randomSecondChromosome, this.populations).get();

        if (firstFitness > secondFitness)
            return getChromosomesAndFitnessByIndex(randomFirstChromosome, this.populations).get();


        return getChromosomesAndFitnessByIndex(randomSecondChromosome, this.populations).get();
    }

    public Map<List<Integer>, Integer> roulette(int sumFitness) {

        int randomValue = new Random().nextInt(sumFitness);
        Map<List<Integer>, Integer> sortedPopulationsByASC = sortChromosomesByFitnessASC();

        for (Map.Entry<List<Integer>, Integer> chromosome : sortedPopulationsByASC.entrySet()){
            randomValue-= chromosome.getValue();
            if (randomValue < 1)
                return Collections.singletonMap(chromosome.getKey(), chromosome.getValue());
        }

        return null;
    }

    public Map<List<Integer>, Integer> replaceWorstFitnessToRandomValue() {
        Map<List<Integer>, Integer>  updatedCurrentPopulation = new LinkedHashMap<>();
        List<List<Integer>> chromosomes = new ArrayList<>(this.populations.keySet());
        List<Integer> fitness = new ArrayList<>(this.populations.values());

        IntStream.range(0, this.populations.size() / 2).forEach(i -> {
            int lastIndex = this.populations.size() - i - 1;
            List<Integer> worstChromosome = chromosomes.get(lastIndex);

            IntStream.range(0, worstChromosome.size()).forEach(j -> {
                worstChromosome.set(j, randomGene(worstChromosome));
            });
            chromosomes.set(lastIndex, worstChromosome);

        });

        IntStream.range(0, chromosomes.size()).forEach(i -> updatedCurrentPopulation.put(chromosomes.get(i), fitness.get(i)));

        return updatedCurrentPopulation;
    }

    public Optional<Map<List<Integer>, Integer>> eletarism() {
        int eletarism = (int) (this.populations.size() * 0.02);
        Map<List<Integer>,Integer> newPopulation = new LinkedHashMap<>();
        IntStream.range(0, eletarism).forEach( index -> newPopulation.put(getChromosomesByIndex(index, this.populations).get(), getFitnessByIndex(index, this.populations).get()));
        return Optional.of(newPopulation);
    }

    public Map<List<Integer>, Integer> sortChromosomesByFitnessDesc(){
        return this.populations.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<List<Integer>, Integer> sortChromosomesByFitnessASC(){
        return this.populations.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public void cultivateGarden(MatrixShow matrixShow, Integer[][] oldMatrix, int order, int row, int column) {
        oldMatrix[row][column] = order;
        matrixShow.setMatrix(oldMatrix);
    }
    public void printBestSolution() {

        Map<List<Integer>, Integer> chromosomes = getChromosomesAndFitnessByIndex(0, this.populations).get();
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
                    cloneGarden = updatedZenGarden(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.TOP);
                }

                else if (gene < ((column + row) - 2)) {
                    int currentRow = gene - column + 1;
                    int currentColumn = column - 1;
                    cloneGarden = updatedZenGarden(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.RIGHT);
                }

                else if (gene < (2 * (column) + row) - 2) {
                    int currentRow = row - 1;
                    int currentColumn = (column * 2 + row - 3) - gene;
                    cloneGarden = updatedZenGarden(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.BOTTOM);
                }
                else {
                    int currentRow = column * 2 + row * 2 - 4 - gene;
                    int currentColumn = 0;
                    cloneGarden = updatedZenGarden(currentRow, currentColumn, cultivatedRake, cloneGarden, 0, Moves.LEFT);

                }
                // cloneGarden.getMatrixShow().printMatrix();
                cultivatedRake++;
            }
        }

        cloneGarden.getMatrixShow().printMatrix();

    }
    public ZenGarden updatedZenGarden(int row, int column, int order, ZenGarden cloneGarden, int path, Moves moves) {
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
                            return zenGarden;
                        else if (right)
                            moves = Moves.LEFT;
                        else if (left)
                            moves = Moves.RIGHT;
                        else
                            return zenGarden;
                        break;
                    case BOTTOM:
                        if (top) {
                            cultivatedGarden++;
                            row--;
                        } else if (currentGameState.border(row, column, moves))
                            return zenGarden;
                        else if (right)
                            moves = Moves.LEFT;
                        else if (left)
                            moves = Moves.RIGHT;
                        else return zenGarden;
                        break;
                    case LEFT:
                        if (right) {
                            cultivatedGarden++;
                            column++;
                        } else if (currentGameState.border(row, column, moves))
                            return zenGarden;
                        else if (top)
                            moves = Moves.BOTTOM;
                        else if (bottom)
                            moves = Moves.TOP;
                        else return zenGarden;

                        break;
                    case RIGHT:
                        if (left) {
                            column--;
                            cultivatedGarden++;
                        } else if (currentGameState.border(row, column, moves))
                            return zenGarden;
                        else if (top) moves = Moves.BOTTOM;
                        else if (bottom) moves = Moves.TOP;
                        else return zenGarden;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        return zenGarden;
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
