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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day06Part01 {
    public static void main(String... args) throws Exception {

        ArrayList<Game> games = new ArrayList<>();

        // read all 2 lines from file Day06txt, extract numbers,add them to line1 and line2, create game and add it to games
        List<String> lines = Files.readAllLines(Path.of("Input/Day06.txt"));
        ArrayList<Integer> line1 = new ArrayList<>(findNumber(lines.get(0)));
        ArrayList<Integer> line2 = new ArrayList<>(findNumber(lines.get(1)));
        for (int i = 0; i < line1.size(); i++) {
            games.add(new Game(line1.get(i), line2.get(i)));
        }


        ArrayList<Integer> scores = new ArrayList<>();
        games.forEach(game -> {
            int score = 0;
            for (int buttonTime = 0; buttonTime < game.getDuration(); buttonTime++) {
                if (calculateDistance(buttonTime, game.getDuration()) > game.getDistance()) {
                    score++;
                }
            }

            scores.add(score);
        });


        AtomicInteger product = new AtomicInteger(1);
        scores.forEach(score -> product.updateAndGet(v -> v * score));
        System.out.println("result = " + product);
    }

    private static int calculateDistance(int buttonTime, int gameDuration) {
        return (gameDuration - buttonTime) * buttonTime;
    }

    private static List<Integer> findNumber(String stringToSearch) {
        Pattern numberPattern = Pattern.compile("-?\\d+");
        Matcher matcher = numberPattern.matcher(stringToSearch);

        List<Integer> numberList = new ArrayList<>();
        while (matcher.find()) {
            numberList.add(Integer.parseInt(matcher.group()));
        }

        return numberList;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    static class Game {
        int duration;
        int distance;
    }
}
