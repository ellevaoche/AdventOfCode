package advent;

// https://adventofcode.com/2023/day/4

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day04Part02 {
    public static void main(String... args) throws Exception {

        ArrayList<Card> cards = new ArrayList<>();

        // read all Lines from file Day03.txt, map them to a Card Record and add them to the cards list
        Files.readAllLines(Path.of("Input/Day04.txt")).forEach(line -> {

            // Extract the card id from the line
            int cardId = Integer.parseInt(findIntegers(line.substring(0, line.indexOf(":"))).get(0));

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
            cards.add(new Card(cardId, winningNumbers, ownNumbers, 1));

        });

        // Iterate over all cards, check how many winning numbers are also own numbers per card and calculate the points and process the copied cards...
        AtomicLong result = new AtomicLong();

        cards.forEach(card -> {

            AtomicInteger amountOfWinningNumbers = new AtomicInteger();

            card.getWinningNumbers().forEach(winningNumber -> {
                if (card.getOwnNumbers().contains(winningNumber)) {
                    amountOfWinningNumbers.incrementAndGet();
                }

            });

            // increment amount of follow Up Cards
            for (int i = 0; i < amountOfWinningNumbers.get(); i++) {
                if (card.getCardId() + i >= cards.size()) {
                    break;
                }

                for (int j = 0; j < card.getAmount(); j++) {
                    cards.get(card.getCardId() + i).incrementAmount();

                }
            }


            // add current amount to result
            result.addAndGet(card.getAmount());
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

    @Getter
    @AllArgsConstructor
    static class Card {

        int cardId;
        ArrayList<Integer> winningNumbers;
        ArrayList<Integer> ownNumbers;
        int amount;

        public void incrementAmount() {
            amount++;
        }

    }
}
