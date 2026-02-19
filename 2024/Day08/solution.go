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
		fmt.Println("Usage: go run solution.go <input_file>")
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

	// Find all unique antinode positions
	antinodes := make(map[Point]bool)
	for _, positions := range antennas {
		n := len(positions)
		for i := 0; i < n; i++ {
			for j := i + 1; j < n; j++ {
				a, b := positions[i], positions[j]
				dr := b.r - a.r
				dc := b.c - a.c

				// Antinode on side of a (opposite to b)
				p1 := Point{a.r - dr, a.c - dc}
				if p1.r >= 0 && p1.r < rows && p1.c >= 0 && p1.c < cols {
					antinodes[p1] = true
				}

				// Antinode on side of b (opposite to a)
				p2 := Point{b.r + dr, b.c + dc}
				if p2.r >= 0 && p2.r < rows && p2.c >= 0 && p2.c < cols {
					antinodes[p2] = true
				}
			}
		}
	}

	fmt.Println(len(antinodes))
}
