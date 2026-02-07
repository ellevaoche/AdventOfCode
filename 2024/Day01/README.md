# Day 1: Historian Hysteria

Compare two lists of location IDs to help The Historians reconcile their notes.

## Part 1

**Task:** Calculate the total distance between two lists.

Sort both lists, pair them up, and sum absolute differences:
```go
slices.Sort(left)
slices.Sort(right)
for i := range left {
    total += abs(left[i] - right[i])
}
```

Example: `[1,2,3,3,3,4]` vs `[3,3,3,4,5,9]` → Distances: 2+1+0+1+2+5 = **11**

## Part 2

**Task:** Calculate a similarity score.

Count occurrences in right list, multiply each left value by its count:
```go
counts := make(map[int]int)
for _, v := range right { counts[v]++ }
for _, v := range left { score += v * counts[v] }
```

Example: `3` appears 3× in right → 3*3=9, sum all = **31**

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/1)
