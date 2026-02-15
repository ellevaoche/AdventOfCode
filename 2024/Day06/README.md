# Day 6: Guard Gallivant

Simulate a guard patrolling a grid following strict movement rules to determine which positions are visited.

## Part 1

**Task:** Count how many distinct positions the guard visits before leaving the mapped area.

The guard starts facing up and follows this protocol:
- If there is an obstacle (`#`) directly ahead, turn right 90°.
- Otherwise, step forward.

```go
for {
    nr, nc := r+dx[dir], c+dy[dir]
    if outOfBounds(nr, nc) { break }
    if grid[nr][nc] == '#' {
        dir = (dir + 1) % 4
    } else {
        r, c = nr, nc
        visited[[2]int{r, c}] = true
    }
}
```

Example: 10×10 grid with guard at `^` → **41** distinct positions visited.

## Part 2

**Task:** Find how many positions could hold a new obstruction that would trap the guard in an infinite loop.

Only positions on the original patrol path need testing. For each candidate, temporarily place an obstacle and simulate — if the guard revisits any `(row, col, direction)` state, it's a loop.

```go
func causesLoop(grid, startR, startC, dir) bool {
    seen := map[state]bool{}
    for {
        if seen[{r, c, dir}] { return true }
        // simulate step or turn...
    }
}
```

Example: 10×10 grid → **6** valid obstruction positions.

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/6)
