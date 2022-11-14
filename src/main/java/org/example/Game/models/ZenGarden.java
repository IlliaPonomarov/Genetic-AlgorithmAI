package org.example.Game.models;

import org.example.Game.MatrixShow;
import org.example.Game.repositories.ZenGardenRepo;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class ZenGarden {
    private UUID id;
    private Set<Stone> stones;
    private Monk monk;

    private int width;

    private int height;
    private MatrixShow matrixShow;


    public ZenGarden(BuilderZenGarden builderZenGarden){
        this.id = builderZenGarden.id;
        this.stones = builderZenGarden.stones;
        this.monk = builderZenGarden.monk;
        this.width = builderZenGarden.width;
        this.height = builderZenGarden.height;
        this.matrixShow = new MatrixShow(this);
    }

    public ZenGarden() {
    }

    public static class BuilderZenGarden {
        // required
        private UUID id;
        private Set<Stone> stones;

        // optional
        private Monk monk;
        private int width;
        private int height;

        public BuilderZenGarden(Set<Stone> stones) {
            this.id = UUID.randomUUID();
            this.stones = stones;
        }

        public BuilderZenGarden monk(Monk monk) {
            this.monk = monk;
            return this;
        }

        public BuilderZenGarden width(int width) {
            this.width = width;
            return this;
        }

        public BuilderZenGarden height(int height) {
            this.height = height;
            return this;
        }

        public ZenGarden build() {

            return new ZenGarden(this);
        }


    }

    public int emptyCellInZenGarden() {
        return this.height * this.width - this.stones.size();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<Stone> getStones() {
        return stones;
    }

    public void setStones(Set<Stone> stones) {
        this.stones = stones;
    }

    public Monk getMonk() {
        return monk;
    }

    public void setMonk(Monk monk) {
        this.monk = monk;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public MatrixShow getMatrixShow() {
        return matrixShow;
    }

    public void setMatrixShow(MatrixShow matrixShow) {
        this.matrixShow = matrixShow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZenGarden)) return false;
        ZenGarden zenGarden = (ZenGarden) o;
        return width == zenGarden.width && height == zenGarden.height && Objects.equals(id, zenGarden.id) && Objects.equals(stones, zenGarden.stones) && Objects.equals(monk, zenGarden.monk) && Objects.equals(matrixShow, zenGarden.matrixShow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stones, monk, width, height, matrixShow);
    }

    @Override
    public String toString() {
        return "ZenGarden{" +
                "id=" + id +
                ", stones=" + stones +
                ", monk=" + monk +
                ", width=" + width +
                ", height=" + height +

                '}';
    }

    @Override
    public ZenGarden clone() {

        return new BuilderZenGarden(this.stones).height(this.height).width(this.width).monk(this.monk).build();
    }
}
