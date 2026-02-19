package main

import (
	"bufio"
	"fmt"
	"os"
)

type Point struct {
	r, c int
}

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: go run solution_part2.go <input_file>")
		os.Exit(1)
	}

	file, err := os.Open(os.Args[1])
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error opening file: %v\n", err)
		os.Exit(1)
	}
	defer file.Close()

	var grid []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		grid = append(grid, scanner.Text())
	}

	rows := len(grid)
	cols := len(grid[0])

	// Collect antenna positions grouped by frequency
	antennas := make(map[byte][]Point)
	for r := 0; r < rows; r++ {
		for c := 0; c < cols; c++ {
			ch := grid[r][c]
			if ch != '.' {
				antennas[ch] = append(antennas[ch], Point{r, c})
			}
		}
	}

	// Find all unique antinode positions with resonant harmonics
	antinodes := make(map[Point]bool)
	for _, positions := range antennas {
		n := len(positions)
		for i := 0; i < n; i++ {
			for j := i + 1; j < n; j++ {
				a, b := positions[i], positions[j]
				dr := b.r - a.r
				dc := b.c - a.c

				// Walk in both directions along the line, including antenna positions
				// Direction: from a away from b
				p := a
				for p.r >= 0 && p.r < rows && p.c >= 0 && p.c < cols {
					antinodes[p] = true
					p = Point{p.r - dr, p.c - dc}
				}

				// Direction: from b away from a
				p = b
				for p.r >= 0 && p.r < rows && p.c >= 0 && p.c < cols {
					antinodes[p] = true
					p = Point{p.r + dr, p.c + dc}
				}
			}
		}
	}

	fmt.Println(len(antinodes))
}
