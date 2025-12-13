package advent;

// https://adventofcode.com/2023/day/5

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day05Part01 {
    public static void main(String... args) throws Exception {

        // read from file Day05.txt. Categories are separated by an empty line.
        String fileContent = Files.readString(Path.of("Input/Day05.txt"));
        ArrayList<String> textBlocks = new ArrayList<>(Arrays.asList(fileContent.split("\r\n\r\n")));

        // Extract seeds
        List<BigInteger> seeds  = new ArrayList<>();
        findNumbers(textBlocks.get(0)).forEach(number -> seeds.add(new BigInteger(number)));

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

        //Determine locationNumbers for each seed and find the lowest one
        List<BigInteger> locationNumbers = new ArrayList<>();
        seeds.forEach(seed -> {
            BigInteger currentNumber = seed;
            currentNumber = mapSource2Destination(currentNumber, seed2SoilMap);
            currentNumber = mapSource2Destination(currentNumber, soil2FertilizerMap);
            currentNumber = mapSource2Destination(currentNumber, fertilizer2WaterMap);
            currentNumber = mapSource2Destination(currentNumber, water2LightMap);
            currentNumber = mapSource2Destination(currentNumber, light2TemperatureMap);
            currentNumber = mapSource2Destination(currentNumber, temperature2HumidityMap);
            currentNumber = mapSource2Destination(currentNumber, humidity2LocationMap);
            locationNumbers.add(currentNumber);
        });

        System.out.println(locationNumbers.stream().min(BigInteger::compareTo).orElse(BigInteger.ZERO));
    }

    private static void extractMapsAndAddToList(String s, List<MyMap> mapList) {
        s.lines().skip(1).forEach(line -> {
            List<String> numbers = findNumbers(line);
            mapList.add(
                    new MyMap(
                            new BigInteger(numbers.get(0)),
                            new BigInteger(numbers.get(1)),
                            new BigInteger(numbers.get(2))
                    )
            );
        });
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    static class MyMap {

        private BigInteger destinationRangeStart;
        private BigInteger sourceRangeStart;
        private BigInteger rangeLength;

        public boolean matchesToSourceRange(BigInteger inputNumber) {
            return inputNumber.compareTo(sourceRangeStart) >= 0 && inputNumber.compareTo(sourceRangeStart.add(rangeLength)) < 0;
        }

        public Optional<BigInteger> getDestinationNumber(BigInteger inputNumber) {

            if (!matchesToSourceRange(inputNumber)) {
                return Optional.empty();
            }

            return Optional.of(destinationRangeStart.add(inputNumber.subtract(sourceRangeStart)));
        }

    }

    private static BigInteger mapSource2Destination(BigInteger inputNumber, List<MyMap> myMaps) {
        Optional<BigInteger> destinationNumber = Optional.empty();

        for (MyMap myMap : myMaps) {
            if (myMap.matchesToSourceRange(inputNumber)) {
                destinationNumber = myMap.getDestinationNumber(inputNumber);
                break;
            }
        }

        return destinationNumber.orElse(inputNumber);
    }

    private static List<String> findNumbers(String stringToSearch) {
        Pattern integerPattern = Pattern.compile("-?\\d+");
        Matcher matcher = integerPattern.matcher(stringToSearch);

        List<String> numberList = new ArrayList<>();
        while (matcher.find()) {
            numberList.add(matcher.group());
        }

        return numberList;
    }
}
