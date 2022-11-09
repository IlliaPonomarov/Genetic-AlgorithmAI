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

    public ZenGarden generateStartGarden() {

        int randomCountOfStones = (int) Math.floor(Math.random() * (6 - 1 + 1) + 1);
        Set<Stone> stones = randomStoneGenerator(randomCountOfStones);
        ZenGarden zenGarden = new ZenGarden();
        return new ZenGarden.BuilderZenGarden(stones).width(12).height(10).build();
    }

    public Monk addMonkToRandomPosition(ZenGarden zenGarden) {
        int x = 0;
        int y = 0;



        // can be y ( column ) position: 0, 12
        // can be x ( row ) position: 0, 10


        return null;
    }

    public Set<Stone> randomStoneGenerator(int randomCountOfStones) {
        Set<Stone> stones = new HashSet<>();



        for (int i = 0; i < randomCountOfStones; i++) {
            int randomSize = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
            int randomRowPosition = (int) Math.floor(Math.random() * 9);
            int randomColumnPosition = (int) Math.floor(Math.random() * 11);

            Turns randomTurn = Turns.values()[new Random().nextInt(Turns.values().length)];
            stones.add(new Stone(randomRowPosition, randomColumnPosition, randomTurn));
        }

        return stones;
    }

}
