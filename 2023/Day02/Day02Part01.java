package advent;

// https://adventofcode.com/2023/day/2

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class Day02Part01 {
    public static void main(String... args) throws Exception {

        // Define DataStructure for Games
        HashMap<Integer, ArrayList<HashMap<String, Integer>>> games = new HashMap<>();

        // read all Lines from file Day02.txt and map them to the DataStructure
        Files.readAllLines(Path.of("Input/Day02.txt")).forEach(line -> {

            //Examples:
            // Game 1: 4 green, 2 blue; 1 red, 1 blue, 4 green; 3 green, 4 blue, 1 red; 7 green, 2 blue, 4 red; 3 red, 7 green; 3 red, 3 green
            // Game 2: 1 blue, 11 red, 1 green; 3 blue, 2 red, 4 green; 11 red, 2 green, 2 blue; 13 green, 5 red, 1 blue; 4 green, 8 red, 3 blue
            // Game 3: 9 red, 2 blue; 4 blue, 2 green, 1 red; 7 red, 4 blue, 3 green; 3 blue, 6 red; 9 blue, 4 red; 3 red

            // Extract GameId from line:
            var gameId = Integer.parseInt(line.substring(5, line.indexOf(":")));

            // Extract cube revelations from line:
            var revelationList = new ArrayList<HashMap<String, Integer>>();
            Arrays.stream(line.substring(line.indexOf(":") + 1).split(";")).forEach(revelation -> {

                var revelationMap = new HashMap<String, Integer>();

                //Split into color and amount:
                Arrays.stream(revelation.split(",")).forEach(revelationPart -> {
                    var color = revelationPart.substring(revelationPart.lastIndexOf(" ") + 1);
                    var amount = Integer.parseInt(revelationPart.replaceAll("\\D", ""));
                    revelationMap.put(color, amount);
                });

                // Add revelationMap to revelationList
                revelationList.add(revelationMap);
            });

            // Add revelationList to games
            games.put(gameId, revelationList);

        });


        // Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes.
        // Sum the gameIds of all possible games:
        var result = new AtomicInteger();

        games.forEach((gameId, revelationList) -> {

            AtomicBoolean enoughRed = new AtomicBoolean(true);
            AtomicBoolean enoughGreen = new AtomicBoolean(true);
            AtomicBoolean enoughBlue = new AtomicBoolean(true);

            revelationList.forEach(revelationMap -> {
                var red = revelationMap.getOrDefault("red", 0);
                var green = revelationMap.getOrDefault("green", 0);
                var blue = revelationMap.getOrDefault("blue", 0);

                if (red > 12) {
                    enoughRed.set(false);
                }

                if (green > 13) {
                    enoughGreen.set(false);
                }

                if (blue > 14) {
                    enoughBlue.set(false);
                }

            });

            if (enoughRed.get() && enoughGreen.get() && enoughBlue.get()) {
                result.addAndGet(gameId);
            }

        });

        System.out.println("My result: " + result);

    }
}
