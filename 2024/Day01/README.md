# Day 1: Historian Hysteria

Compare two lists of location IDs to help The Historians reconcile their notes.

## Part 1

**Task:** Calculate the total distance between two lists.

1. Sort both lists independently
2. Pair smallest with smallest, second-smallest with second-smallest, etc.
3. Calculate absolute difference for each pair
4. Sum all differences

**Example:**
```
Left:  3, 4, 2, 1, 3, 3
Right: 4, 3, 5, 3, 9, 3

Sorted Left:  1, 2, 3, 3, 3, 4
Sorted Right: 3, 3, 3, 4, 5, 9

Distances: 2 + 1 + 0 + 1 + 2 + 5 = 11
```

## Part 2

**Task:** Calculate a similarity score.

For each number in the left list, count how often it appears in the right list, then multiply and sum.

**Example:**
```
3 appears 3 times in right → 3 * 3 = 9
4 appears 1 time  in right → 4 * 1 = 4
2 appears 0 times in right → 2 * 0 = 0
1 appears 0 times in right → 1 * 0 = 0
3 appears 3 times in right → 3 * 3 = 9
3 appears 3 times in right → 3 * 3 = 9

Similarity Score: 9 + 4 + 0 + 0 + 9 + 9 = 31
```

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/1)
