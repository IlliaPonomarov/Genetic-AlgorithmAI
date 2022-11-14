package org.example.Game.services;

import org.example.Game.models.Monk;
import org.example.Game.models.Stone;
import org.example.Game.models.Turns;
import org.example.Game.models.ZenGarden;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ZenGardenService {

    public ZenGardenService() {}

    public ZenGarden generateStartGarden(int width, int height) {

        int randomCountOfStones = (int) Math.floor(Math.random() * (6 - 1 + 1) + 1);
        Set<Stone> stones = randomStoneGenerator(randomCountOfStones, width, height);
        ZenGarden zenGarden = new ZenGarden();
        return new ZenGarden.BuilderZenGarden(stones).width(width).height(height).build();
    }

    public Set<Stone> randomStoneGenerator(int randomCountOfStones, int width, int height) {
        Set<Stone> stones = new HashSet<>();

        for (int i = 0; i < randomCountOfStones; i++) {
            int randomRowPosition = (int) Math.floor(Math.random() * (height - 1));
            int randomColumnPosition = (int) Math.floor(Math.random() * (width -1));

            Turns randomTurn = Turns.values()[new Random().nextInt(Turns.values().length)];
            stones.add(new Stone(randomRowPosition, randomColumnPosition, randomTurn));
        }

        return stones;
    }

}
