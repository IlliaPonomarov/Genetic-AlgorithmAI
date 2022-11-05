package org.example.Game.services;

import org.example.Game.models.Stone;
import org.example.Game.models.ZenGarden;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class ZenGardenService {



    public ZenGarden generateStartGardenWithoutMonk() {

        int randomCountOfStones = (int) Math.floor(Math.random() * (6 - 1 + 1) + 1);
        Set<Stone> stones = randomStoneGenerator(randomCountOfStones);

        return new ZenGarden.BuilderZenGarden(stones).width(12).height(10).build();
    }

    public Set<Stone> randomStoneGenerator(int randomCountOfStones) {
        Set<Stone> stones = new HashSet<>();
        int randomSize = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
        int randomRowPosition = (int) Math.floor(Math.random() * 9);
        int randomColumnPosition = (int) Math.floor(Math.random() * 11);
        int randomTurn = (int) Math.floor(Math.random() * 1);


        for (int i = 0; i < randomCountOfStones; i++) stones.add(new Stone(randomRowPosition, randomColumnPosition, randomSize, randomTurn));

        return stones;
    }

}
