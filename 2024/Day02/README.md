# Day 2: Red-Nosed Reports

Analyze reactor safety reports to determine which ones are safe. Each report is a list of levels (numbers).

## Part 1

**Task:** Count reports where levels are **monotonic** (all increasing or decreasing) with steps of **1-3**.

Check direction consistency and step size:
```go
for i := 1; i < len(levels); i++ {
    diff := levels[i] - levels[i-1]
    if (increasing && diff <= 0) || (!increasing && diff >= 0) {
        return false  // direction changed
    }
    if abs(diff) < 1 || abs(diff) > 3 {
        return false  // step too small or large
    }
}
```

Example: `7 6 4 2 1` → Safe (decreasing by 1-2), `1 2 7 8 9` → Unsafe (2→7 is +5)

## Part 2

**Task:** Count safe reports, allowing the **Problem Dampener** to remove one bad level.

Brute force: try removing each level and check if the result is safe:
```go
for skip := 0; skip < len(levels); skip++ {
    modified := removeIndex(levels, skip)
    if isSafe(modified) {
        return true
    }
}
```

Example: `1 3 2 4 5` → Remove `3` → `1 2 4 5` → Safe ✓

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/2)
