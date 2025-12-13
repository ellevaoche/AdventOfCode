package advent;

// https://adventofcode.com/2023/day/6

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day06Part02 {
    public static void main(String... args) throws Exception {

        // read all 2 lines from file Day06txt, extract numbers,add them to line1 and line2, create game and add it to games
        List<String> lines = Files.readAllLines(Path.of("Input/Day06.txt"));
        Game game = new Game(findNumberAndConcatThem(lines.get(0)), findNumberAndConcatThem(lines.get(1)));


        // Determine how many ways there are to beat the record distance
        long score = 0;
        System.out.println("game = " + game);

        for (long buttonTime = 0; buttonTime < game.getDuration(); buttonTime++) {
            if (calculateDistance(buttonTime, game.getDuration()) > game.getDistance()) {
                score++;
            }
        }


        System.out.println("result = " + score);
    }

    private static long calculateDistance(long buttonTime, long gameDuration) {
        return (gameDuration - buttonTime) * buttonTime;
    }

    private static long findNumberAndConcatThem(String stringToSearch) {
        Pattern numberPattern = Pattern.compile("-?\\d+");
        Matcher matcher = numberPattern.matcher(stringToSearch);

        StringBuilder number = new StringBuilder();
        while (matcher.find()) {
            number.append(matcher.group());
        }

        return Long.parseLong(number.toString());
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    static class Game {
        long duration;
        long distance;
    }
}
