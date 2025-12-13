package advent;

// https://adventofcode.com/2023/day/4

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day04Part01 {
    public static void main(String... args) throws Exception {

        ArrayList<Card> cards = new ArrayList<>();

        // read all Lines from file Day03.txt, map them to a Card Record and add them to the cards list
        Files.readAllLines(Path.of("Input/Day04.txt")).forEach(line -> {

            // Extract the game id from the line
            String cardId = line.substring(0, line.indexOf(":"));

            // Extract the winning numbers from the line
            ArrayList<Integer> winningNumbers = new ArrayList<>();
            findIntegers(line.substring(line.indexOf(":") + 1, line.indexOf("|"))).stream()
                    .map(Integer::parseInt)
                    .forEach(winningNumbers::add);

            // Extract the own numbers from the line
            ArrayList<Integer> ownNumbers = new ArrayList<>();
            findIntegers(line.substring(line.indexOf("|") + 1)).stream()
                    .map(Integer::parseInt)
                    .forEach(ownNumbers::add);

            // Create a new Card Record and add it to the cards list
            cards.add(new Card(cardId, winningNumbers, ownNumbers));

        });

        // Iterate over all cards, check how many winning numbers are also own numbers per card and calculate the points
        AtomicInteger result = new AtomicInteger();

        cards.forEach(card -> {

            AtomicInteger points = new AtomicInteger();

            card.winningNumbers().forEach(winningNumber -> {
                if (card.ownNumbers().contains(winningNumber)) {
                    if (points.get() == 0) {
                        points.set(1);
                    } else {
                        points.updateAndGet(v -> v * 2);
                    }
                }

            });

            // add points to result
            result.addAndGet(points.get());
        });


        System.out.println("My result: " + result);

    }

    static List<String> findIntegers(String stringToSearch) {
        Pattern integerPattern = Pattern.compile("-?\\d+");
        Matcher matcher = integerPattern.matcher(stringToSearch);

        List<String> integerList = new ArrayList<>();
        while (matcher.find()) {
            integerList.add(matcher.group());
        }

        return integerList;
    }

    record Card(String CardId, ArrayList<Integer> winningNumbers, ArrayList<Integer> ownNumbers) {

    }
}
