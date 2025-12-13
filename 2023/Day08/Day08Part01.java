package advent;

// https://adventofcode.com/2023/day/8

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day08Part01 {
    public static void main(String... args) throws Exception {

        // read from file Day08.txt. Categories are separated by an empty line.
        String fileContent = Files.readString(Path.of("Input/Day08.txt"));
        ArrayList<String> textBlocks = new ArrayList<>(Arrays.asList(fileContent.split("\r\n\r\n")));

        // Extract Direction Instructions
        List<Character> directions = new ArrayList<>();
        for (char c : textBlocks.get(0).toCharArray()) {
            directions.add(c);
        }

        // Extract Nodes
        HashMap<String, Node> nodes = new HashMap<>();
        textBlocks.get(1).lines().forEach(line -> {
            String key = line.substring(0, 3);
            String left = line.substring(7, 10);
            String right = line.substring(12, 15);
            nodes.put(key, new Node(left, right));
        });


        // Navigate through the Nodes and count the step until ZZZ is reached
        final String[] currentKey = {"AAA"};
        final int[] steps = {0};

        while (!currentKey[0].equals("ZZZ")) {
            directions.forEach(direction -> {
                Node node = nodes.get(currentKey[0]);
                if (direction.equals('L')) {
                    currentKey[0] = node.getLeft();
                } else {
                    currentKey[0] = node.getRight();
                }
                steps[0]++;

                if (currentKey[0].equals("ZZZ")) {
                    return;
                }
            });
        }

        System.out.println("result: " + steps[0]);


    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    static class Node {
        private String left;
        private String right;

    }
}
