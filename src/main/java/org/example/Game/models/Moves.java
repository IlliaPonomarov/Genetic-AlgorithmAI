package org.example.Game.models;
interface Fitness {
    boolean fitness(boolean top, boolean bottom, boolean right, boolean left, int row, int column, int cultivatedGarden, Moves move);
}
public enum Moves {
    LEFT , RIGHT, TOP , BOTTOM
}
