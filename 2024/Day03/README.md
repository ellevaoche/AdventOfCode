# Day 3: Mull It Over

Parse corrupted memory to find valid `mul(X,Y)` instructions and sum their products.

## Part 1

**Task:** Find all valid `mul(X,Y)` patterns (X, Y are 1-3 digit numbers) and sum the products.

Use regex to extract valid instructions:
```go
re := regexp.MustCompile(`mul\((\d{1,3}),(\d{1,3})\)`)
matches := re.FindAllStringSubmatch(input, -1)
```

Example: `xmul(2,4)%&mul[3,7]!mul(5,5)` → `2*4 + 5*5 = 33`

## Part 2

**Task:** Handle `do()` and `don't()` instructions that enable/disable future `mul` operations.

Track enabled state while processing matches:
```go
re := regexp.MustCompile(`mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)`)
enabled := true
for _, match := range re.FindAllStringSubmatch(input, -1) {
    switch match[0] {
    case "do()":    enabled = true
    case "don't()": enabled = false
    default:        if enabled { sum += x * y }
    }
}
```

Example: `xmul(2,4)&don't()_mul(5,5)do()?mul(8,5)` → `2*4 + 8*5 = 48`

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/3)
