package com.evieclient.examples;

import zone.nora.slothpixel.Slothpixel;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.bedwars.BedWars;

public class SlothpixelExample {
    public static void main() {
        // Make an instance of the Slothpixel API.
        Slothpixel slothpixel = new Slothpixel();
        // Save a request as a local variable.
        Player player = slothpixel.getPlayer("twisttaan");

        System.out.println("UUID: " + player.getUuid());
        System.out.println("Karma: " + player.getKarma());
        System.out.println("Discord: " + player.getLinks().getDiscord());

        // Save a specific game's stats.
        BedWars bedWars = player.getStats().getBedWars();

        System.out.println("Coins: " + bedWars.getCoins());
        System.out.println("Stars: " + bedWars.getLevel());
        //Slothpixel DOCS: https://docs.slothpixel.me/
    }
}
