package advent;

// https://adventofcode.com/2023/day/3

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day03Part02 {
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

            // find all gear symbols and add them to the symbols list
            for (char character : line.toCharArray()) {

                // check if character is a gear symbol
                if (character == '*') {
                    symbols.add(new Symbol(character, columnNumber, lineNumber));
                }

                columnNumber++;
            }

            lineNumber++;
        }


        // Iterate over all gear symbols and check if they have two adjacent numbers
        AtomicInteger result = new AtomicInteger();

        symbols.forEach(symbol -> {

            ArrayList<Number> gearNumbers = new ArrayList<>();

            numbers.stream()
                    .filter(number ->
                    number.y() >= symbol.y() -1 && number.y() <= symbol.y() + 1
                    && number.xEnd() >= symbol.x() - 1 && number.xStart() <= symbol.x() + 1)
                    .forEach(gearNumbers::add);

            if (gearNumbers.size() == 2) {
                result.addAndGet(gearNumbers.get(0).value() * gearNumbers.get(1).value());
            }

        });

        System.out.println("My result: " + result);
    }


    record Symbol(char symbol, int x, int y) {

    }

    record Number(int value, int xStart, int xEnd, int y) {

    }

}