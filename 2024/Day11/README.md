# Day 11: Plutonian Pebbles

Stones arranged in a straight line change with each blink according to specific rules.

**Rules (applied in order):**
1. If stone is `0` → becomes `1`
2. If stone has an even number of digits → split into two stones (left half / right half, no leading zeros)
3. Otherwise → stone's number is multiplied by `2024`

## Part 1

**Task:** How many stones will you have after blinking **25 times**?

```go
// Use a map to track counts of each stone value (avoid storing all stones individually)
counts := map[int]int{}
for _, n := range stones {
    counts[n]++
}
```

Example: `125 17` → after 25 blinks → **55312**

## Part 2

**Task:** How many stones will you have after blinking **75 times**?

Same algorithm as Part 1, but with 75 iterations. The key insight is to count stones by value rather than tracking their order, since order doesn't matter for the count.

Example: `125 17` → after 75 blinks → **65601038650482**

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/11)
