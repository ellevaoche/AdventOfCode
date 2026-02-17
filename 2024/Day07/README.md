# Day 7: Bridge Repair

Determine which calibration equations can be made true by inserting `+` and `*` operators between operands (evaluated left-to-right, no precedence).

## Part 1

**Task:** Find all equations where some combination of `+` and `*` operators produces the test value. Sum those test values.

For each equation, try all 2^(n-1) operator combinations via recursion. Operators are evaluated strictly left-to-right:

```go
func canMake(target, current int, nums []int) bool {
    if len(nums) == 0 {
        return current == target
    }
    return canMake(target, current+nums[0], nums[1:]) ||
           canMake(target, current*nums[0], nums[1:])
}
```

Example: `3267: 81 40 27` → `81 + 40 * 27 = 3267` ✓

## Part 2

**Task:** Same as Part 1, but with a third operator: concatenation (`||`) which joins digits (e.g. `15 || 6 = 156`). Sum test values of all solvable equations.

Extend the recursive search to also try concatenation at each position:

```go
func concat(a, b int) int {
    mul := 1
    for tmp := b; tmp > 0; tmp /= 10 {
        mul *= 10
    }
    return a*mul + b
}
```

Example: `192: 17 8 14` → `17 || 8 + 14 = 192` ✓

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/7)
