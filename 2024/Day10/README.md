# Day 10: Hoof It

Find valid hiking trails on a topographic map that strictly increase in height from 0 to 9.

## Part 1

**Task:** Calculate the sum of the scores of all trailheads (the number of `9` height positions reachable from each `0` height position).

A hiking trail must be formed by steps up, down, left, or right, increasing by exactly 1 height at each step. We can find all reachable end positions from a trailhead using Depth-First Search (DFS) or Breadth-First Search (BFS) while keeping track of the distinct `9`s we have reached.

```go
// Reachability via DFS
reachable := map[[2]int]bool{}
// ... DFS traversal from height 0 ...
if grid[nr][nc] == 9 {
    reachable[[2]int{nr, nc}] = true
}
```

Example: `test.txt` → **36**

## Part 2

**Task:** Calculate the sum of the ratings of all trailheads (the number of distinct valid paths from each `0` height position to any `9` height position).

A single `9` height position might be reachable via multiple paths, each counting as a separate trail. This can be efficiently computed by memoizing the number of paths from any given point to any valid end point.

```go
// Memoized recursive DFS to count distinct paths
if grid[r][c] == 9 {
    return 1
}
if memo[r][c] != -1 {
    return memo[r][c]
}
total := 0
// ... check valid neighbors ...
if grid[nr][nc] == grid[r][c]+1 {
    total += countPaths(nr, nc)
}
memo[r][c] = total
return total
```

Example: `test.txt` → **81**

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/10)
