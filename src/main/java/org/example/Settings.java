package org.example;

public class Settings {

    private final Integer MUTATION = 20;
    private Integer generation;
    private Integer countOfPopulation;

    public Settings(Integer generation, Integer countOfPopulation) {
        this.generation = generation;
        this.countOfPopulation = countOfPopulation;
    }

    public Integer getMUTATION() {
        return MUTATION;
    }

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public Integer getCountOfPopulation() {
        return countOfPopulation;
    }

    public void setCountOfPopulation(Integer countOfPopulation) {
        this.countOfPopulation = countOfPopulation;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "MUTATION=" + MUTATION +
                ", generation=" + generation +
                ", countOfPopulation=" + countOfPopulation +
                '}';
    }
}
