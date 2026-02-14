# Day 5: Print Queue

Validate and sort page update sequences based on ordering rules.

## Part 1

**Task:** Find updates already in correct order according to `X|Y` rules, sum their middle page numbers.

For each update, check all applicable rules — if both pages X and Y are present, X must appear before Y:
```go
pos := make(map[int]int)
for i, page := range update { pos[page] = i }
for rule := range rules {
    if pos[rule[0]] > pos[rule[1]] { return false }
}
```

Example: 6 updates → 3 correct → middle pages sum to **143**

## Part 2

**Task:** Take incorrectly-ordered updates, sort them using the ordering rules, sum their middle page numbers.

Use the rules directly as a sort comparator:
```go
sort.Slice(update, func(i, j int) bool {
    return rules[[2]int{update[i], update[j]}]
})
```

Example: 3 incorrect updates → sorted middle pages sum to **123**

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/5)
