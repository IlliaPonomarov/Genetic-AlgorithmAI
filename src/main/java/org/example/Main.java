package org.example;

import org.example.Game.models.ZenGarden;
import org.example.Game.services.ZenGardenService;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {

        ZenGardenService zenGardenService = new ZenGardenService();
        ZenGarden zenGarden = zenGardenService.generateStartGardenWithoutMonk();

        System.out.println(zenGarden.toString());
    }
}
