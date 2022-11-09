package org.example;

import java.util.Arrays;
import java.util.Objects;

public class Chromosome {

    private Integer[] chromosome;
    private Byte gene;
    private Integer width;
    private Integer height;
    private Integer maxField;

    public Chromosome(int width, int height, int countOfStones) {
        this.width = width;
        this.height = height;
        this.chromosome = new Integer[(width + height + countOfStones) / 2];
        this.maxField = (((this.width + this.height) * 2) - 4) - 1;
    }

    private void generateRandomChromosomeValue() {

    }

    public Integer[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(Integer[] chromosome) {
        this.chromosome = chromosome;
    }

    public Byte getGene() {
        return gene;
    }

    public void setGene(Byte gene) {
        this.gene = gene;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getMaxField() {
        return maxField;
    }

    public void setMaxField(Integer maxField) {
        this.maxField = maxField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chromosome)) return false;
        Chromosome that = (Chromosome) o;
        return Arrays.equals(chromosome, that.chromosome) && Objects.equals(gene, that.gene) && Objects.equals(width, that.width) && Objects.equals(height, that.height) && Objects.equals(maxField, that.maxField);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(gene, width, height, maxField);
        result = 31 * result + Arrays.hashCode(chromosome);
        return result;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "chromosome=" + Arrays.toString(chromosome) +
                ", gene=" + gene +
                ", width=" + width +
                ", height=" + height +
                ", maxField=" + maxField +
                '}';
    }
}
