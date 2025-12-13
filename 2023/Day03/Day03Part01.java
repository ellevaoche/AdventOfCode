package advent;

// https://adventofcode.com/2023/day/3

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day03Part01 {
    public static void main(String... args) throws Exception {


        // read all Lines from file Day03.txt
        List<String> lines = Files.readAllLines(Path.of("Input/Day03.txt"));

        // Iterate over all lines and add the symbols and the numbers to their corresponding lists
        List<Symbol> symbols = new ArrayList<>();
        List<Number> numbers = new ArrayList<>();
        int lineNumber = 0;

        for (String line : lines) {
            int columnNumber = 0;

            // find all numbers and add them to the numbers list
            Matcher numberMatcher =  Pattern.compile("\\d+").matcher(line);

            while (numberMatcher.find())
            {
                String numStr = line.substring(numberMatcher.start(), numberMatcher.end());
                Number number = new Number(Integer.parseInt(numStr), numberMatcher.start(), numberMatcher.end() - 1, lineNumber);
                numbers.add(number);
            }

            // find all symbols and add them to the symbols list
            for (char character : line.toCharArray()) {

                // check if character is a real symbol
                if (character != '.' && !Character.isDigit(character)) {
                    symbols.add(new Symbol(character, columnNumber, lineNumber));
                }

                columnNumber++;
            }

            lineNumber++;
        }


        // Iterate over all numbers and check if they have an adjacent symbol
        // If they have an adjacent symbol the value is added to the result
        AtomicInteger result = new AtomicInteger();

        numbers.forEach(number -> {
            // check if one of the symbols is an adjacent one to the current number
            boolean adjacentSymbolFound = symbols.stream()
                    .anyMatch(symbol ->
                            symbol.y() >= number.y() - 1 && symbol.y() <= number.y() + 1
                                    && symbol.x() >= number.xStart() - 1 && symbol.x() <= number.xEnd() + 1);

            if (adjacentSymbolFound) {
                result.addAndGet(number.value());
            }
        });


        System.out.println("My result: " + result);
    }


    record Symbol(char symbol, int x, int y) {

    }

    record Number(int value, int xStart, int xEnd, int y) {

    }

}