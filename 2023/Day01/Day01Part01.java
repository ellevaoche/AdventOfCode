package advent;

// https://adventofcode.com/2023/day/1

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class Day01Part01 {
    public static void main(String ... args) throws Exception {

        AtomicInteger result = new AtomicInteger();

        // read all Lines from file Day01.txt
        Files.readAllLines(Path.of("Input/Day01.txt")).forEach(line -> {

            // find in each line the first digit and the last digit, concat them, parse to int and add to result.
            // Digits could also be spelled out with letters: one, two, three, four, five, six, seven, eight, nine
            // example:
            // line1 = "a1asd23sds4dd" -> first = 1, last = 4 -> concat = "14" -> int = 14
            // line2 = "a1asddsfssddd" -> first = 1, last = 1 -> concat = "11" -> int = 11
            // line3 = "abcone2threexyz" -> first = 1, last = 3 -> concat = "13" -> int = 13
            // line4 = "7pqrstsixteen" -> first = 7, last = 6 -> concat = "76" -> int = 76

            var first = "";
            for (int i = 0; i < line.length() && first.isEmpty(); i++) {
                first = findDigitAtPosition(line, i). orElse("");
            }

            var last = "";
            for (int i = line.length() - 1; i >= 0 && last.isEmpty(); i--) {
                last = findDigitAtPosition(line, i).orElse("");
            }

            result.addAndGet(Integer.parseInt(first + last));

        });

        System.out.println("My result: " + result);

    }

    private static Optional<String> findDigitAtPosition(String line, int i) {

        if (Character.isDigit(line.charAt(i))) {
            return Optional.of(String.valueOf(line.charAt(i)));

        }

        return Optional.empty();
    }
}
