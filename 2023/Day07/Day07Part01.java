package advent;

// https://adventofcode.com/2023/day/7

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

class Day07Part01 {
    public static void main(String... args) throws Exception {

        ArrayList<Hand> hands = new ArrayList<>();

        // read all Lines from file Day07.txt and map them to a Hand
        Files.readAllLines(Path.of("Input/Day07.txt")).forEach(line -> {

            //Examples:
            // 32T3K 765
            // T55J5 684
            // KK677 28
            // KTJJT 220
            // QQQJA 483

            // Create Hands from the input
            String[] split = line.split(" ");
            hands.add(new Hand(split[0], Integer.parseInt(split[1])));

        });

        // Sort Hands by rank
        hands.sort(Hand::compareTo);

        // Print all hands
        hands.forEach(hand -> System.out.println(hand.getHand() + " " + hand.getType() + " " + hand.getBid()));

        // multiply the bid of each hand with its rank and them sum them up
        AtomicInteger sum = new AtomicInteger();

        for (int i = 0; i < hands.size(); i++) {
            sum.addAndGet(hands.get(i).getBid() * (i + 1));
        }

        System.out.println("Sum of all bids: " + sum);



    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    static class Hand implements Comparable<Hand> {
        private String hand;
        private int bid;

        @Override
        public int compareTo(Hand anotherHand) {

            // first try to compare rank
            if (this.getType().rank > anotherHand.getType().rank) {
                return 1;
            } else if (this.getType().rank < anotherHand.getType().rank) {
                return -1;

            } else {
                // if rank is equal, compare card by card until one card is better
                for (int i = 0; i < this.getHand().length(); i++) {
                    if (this.getRankOfCard(i) > anotherHand.getRankOfCard(i)) {
                        return 1;
                    } else if (this.getRankOfCard(i) < anotherHand.getRankOfCard(i)) {
                        return -1;
                    }
                }

                // Both hands are equal
                return 0;
            }
        }

        public TYPE getType() {

            HashMap<Character, Integer> cardMap = new HashMap<>();

            for (char ch: hand.toCharArray()) {

                // Add each char to a cardMap and increment the occurrences
                if (cardMap.containsKey(ch)) {
                    cardMap.put(ch, cardMap.get(ch) + 1);
                } else {
                    cardMap.put(ch, 1);
                }
            }

            // Check for five of a kind
            if (cardMap.size() == 1) {
                return TYPE.FIVE;
            }

            if (cardMap.size() == 2) {
                // Check for full house
                if (cardMap.containsValue(3) && cardMap.containsValue(2)) {
                    return TYPE.FULLHOUSE;
                }
                // Check for four of a kind
                if (cardMap.containsValue(4)) {
                    return TYPE.FOUR;
                }
            }

            if (cardMap.size() == 3) {
                // Check for three of a kind
                if (cardMap.containsValue(3)) {
                    return TYPE.THREE;
                }
                // Check for two pair
                if (cardMap.containsValue(2)) {
                    return TYPE.TWOPAIR;
                }
            }

            // Check for one pair
            if (cardMap.size() == 4) {
                return TYPE.ONEPAIR;
            }

            // Check for High Card
            if (cardMap.size() == 5) {
                return TYPE.HIGHCARD;
            }


            throw new RuntimeException("No type found for hand: " + hand);
        }

        public int getRankOfCard(int index) {
            if (Character.isDigit(getHand().charAt(index))) {
                return Integer.parseInt(String.valueOf(hand.charAt(index)));
            }

            return CARD.valueOf(String.valueOf(hand.charAt(index))).rank;
        }

    }

    public enum CARD {
        A(14),
        K(13),
        Q(12),
        J(11),
        T(10);

        final int rank;

        CARD(int rank) {
            this.rank = rank;
        }

    }

    public enum TYPE {
        FIVE(7),
        FOUR(6),
        FULLHOUSE(5),
        THREE(4),
        TWOPAIR(3),
        ONEPAIR(2),
        HIGHCARD(1);

        final int rank;

        TYPE(int rank) {
            this.rank = rank;
        }

    }
}
