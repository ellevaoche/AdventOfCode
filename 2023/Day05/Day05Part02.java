package advent;

// https://adventofcode.com/2023/day/5

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day05Part02 {
    public static void main(String... args) throws Exception {

        // read from file Day05.txt. Categories are separated by an empty line.
        String fileContent = Files.readString(Path.of("Input/Day05.txt"));
        ArrayList<String> textBlocks = new ArrayList<>(Arrays.asList(fileContent.split("\r\n\r\n")));

        // Extract SeedRanges from seed line
        // Example:
        // seeds: 79 14 55 13 -> seedRanges: [79, 14], [55, 13]
        List<SeedRange> seedRanges = findSeedRanges(textBlocks.get(0));

        // Extract seed2SoilMap
        List<MyMap> seed2SoilMap = new ArrayList<>();
        extractMapsAndAddToList(textBlocks.get(1), seed2SoilMap);

        // Extract soil2FertilizerMap
        List<MyMap> soil2FertilizerMap = new ArrayList<>();
        extractMapsAndAddToList(textBlocks.get(2), soil2FertilizerMap);

        // Extract fertilizer2WaterMap
        List<MyMap> fertilizer2WaterMap = new ArrayList<>();
        extractMapsAndAddToList(textBlocks.get(3), fertilizer2WaterMap);

        // Extract water2LightMap
        List<MyMap> water2LightMap = new ArrayList<>();
        extractMapsAndAddToList(textBlocks.get(4), water2LightMap);

        // Extract light2TemperatureMap
        List<MyMap> light2TemperatureMap = new ArrayList<>();
        extractMapsAndAddToList(textBlocks.get(5), light2TemperatureMap);

        // Extract temperature2HumidityMap
        List<MyMap> temperature2HumidityMap = new ArrayList<>();
        extractMapsAndAddToList(textBlocks.get(6), temperature2HumidityMap);

        // Extract humidity2LocationMap
        List<MyMap> humidity2LocationMap = new ArrayList<>();
        extractMapsAndAddToList(textBlocks.get(7), humidity2LocationMap);


        // map each seed to a location. Keep the lowest location number.
        long lowestLocationNumber = Long.MAX_VALUE;
        for (SeedRange seedRange : seedRanges) {
            System.out.println("current seedRange = " + seedRange);
            for (long i = seedRange.getRangeStart(); i < seedRange.getRangeStart() + seedRange.getRangeLength(); i++) {
                long currentNumber = mapSource2Destination(i, seed2SoilMap);
                currentNumber = mapSource2Destination(currentNumber, soil2FertilizerMap);
                currentNumber = mapSource2Destination(currentNumber, fertilizer2WaterMap);
                currentNumber = mapSource2Destination(currentNumber, water2LightMap);
                currentNumber = mapSource2Destination(currentNumber, light2TemperatureMap);
                currentNumber = mapSource2Destination(currentNumber, temperature2HumidityMap);
                currentNumber = mapSource2Destination(currentNumber, humidity2LocationMap);

                if (currentNumber < lowestLocationNumber) {
                    lowestLocationNumber = currentNumber;
                }
            }
        }

        System.out.println("lowestLocationNumber = " + lowestLocationNumber);

    }

    private static void extractMapsAndAddToList(String s, List<MyMap> mapList) {
        s.lines().skip(1).forEach(line -> {
            List<Long> numbers = findBigIntegers(line);
            mapList.add(
                    new MyMap(
                            numbers.get(0),
                            numbers.get(1),
                            numbers.get(2)
                    )
            );
        });
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    static class MyMap {

        private long destinationRangeStart;
        private long sourceRangeStart;
        private long rangeLength;

        public boolean matchesToSourceRange(long inputNumber) {
            return inputNumber >= sourceRangeStart && inputNumber < sourceRangeStart + rangeLength;
        }

        public Optional<Long> getDestinationNumber(long inputNumber) {

            if (!matchesToSourceRange(inputNumber)) {
                return Optional.empty();
            }

            return Optional.of(destinationRangeStart + inputNumber - sourceRangeStart);
        }

    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    static class SeedRange {

        private long rangeStart;
        private long rangeLength;
    }

    private static long mapSource2Destination(long inputNumber, List<MyMap> myMaps) {
        Optional<Long> destinationNumber = Optional.empty();

        for (MyMap myMap : myMaps) {
            if (myMap.matchesToSourceRange(inputNumber)) {
                destinationNumber = myMap.getDestinationNumber(inputNumber);
                break;
            }
        }

        return destinationNumber.orElse(inputNumber);
    }

    private static List<Long> findBigIntegers(String stringToSearch) {
        Pattern numberPattern = Pattern.compile("-?\\d+");
        Matcher matcher = numberPattern.matcher(stringToSearch);

        List<Long> numberList = new ArrayList<>();
        while (matcher.find()) {
            numberList.add(Long.parseLong(matcher.group()));
        }

        return numberList;
    }

    private static List<SeedRange> findSeedRanges(String stringToSearch) {
        Pattern numberPattern = Pattern.compile("-?\\d+");
        Matcher matcher = numberPattern.matcher(stringToSearch);

        List<Long> numberList = new ArrayList<>();
        while (matcher.find()) {
            numberList.add(Long.parseLong(matcher.group()));
        }

        List<SeedRange> seedRanges = new ArrayList<>();
        for (int i = 0; i < numberList.size(); i += 2) {
            seedRanges.add(new SeedRange(numberList.get(i), numberList.get(i + 1)));
        }

        return seedRanges;
    }
}
