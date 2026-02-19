# Day 8: Resonant Collinearity

Find antinode positions created by pairs of same-frequency antennas on a grid.

## Part 1

**Task:** Count unique antinode locations within the map bounds.

For each pair of antennas with the same frequency, two antinodes are created — one on each side of the pair, at positions where one antenna is twice as far as the other. Antennas are identified by lowercase/uppercase letters or digits.

```go
// For each pair of same-frequency antennas (a, b):
dx, dy := b.x - a.x, b.y - a.y
antinode1 := Point{a.x - dx, a.y - dy}
antinode2 := Point{b.x + dx, b.y + dy}
```

Example: 12×12 grid with `0` and `A` antennas → **14** unique antinode locations.

## Part 2

**Task:** With resonant harmonics, antinodes occur at **every** grid position exactly in line with at least two same-frequency antennas, regardless of distance — including at the antenna positions themselves.

```go
// Walk in both directions along the line through each antenna pair
p := a
for inBounds(p) {
    antinodes[p] = true
    p = Point{p.r - dr, p.c - dc}
}
```

Example: same grid → **34** unique antinode locations.

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/8)
