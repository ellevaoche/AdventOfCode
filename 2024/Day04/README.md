# Day 4: Ceres Search

Search a character grid for specific word patterns.

## Part 1

**Task:** Count all occurrences of `XMAS` in the grid (horizontal, vertical, diagonal, forwards and backwards).

For each cell, check all 8 directions for a complete match:
```go
dirs := [][2]int{{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}}
for i := 0; i < len(word); i++ {
    if grid[r+i*dr][c+i*dc] != word[i] { return false }
}
```

Example: 10×10 grid → 18 occurrences

## Part 2

**Task:** Count X-MAS patterns — two `MAS` strings crossing in an X shape, centered on an `A`.

Check both diagonals through each `A`:
```go
if grid[r][c] != 'A' { continue }
d1 := string([]byte{grid[r-1][c-1], grid[r+1][c+1]})
d2 := string([]byte{grid[r-1][c+1], grid[r+1][c-1]})
if (d1 == "MS" || d1 == "SM") && (d2 == "MS" || d2 == "SM") { count++ }
```

Example: 10×10 grid → 9 occurrences

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/4)
