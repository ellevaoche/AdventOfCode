package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: go run solution_part2.go <input_file>")
		os.Exit(1)
	}

	data, err := os.ReadFile(os.Args[1])
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error reading file: %v\n", err)
		os.Exit(1)
	}

	lines := strings.Split(strings.TrimSpace(string(data)), "\n")
	rows := len(lines)
	cols := len(lines[0])

	grid := make([][]int, rows)
	for r, line := range lines {
		grid[r] = make([]int, cols)
		for c, ch := range line {
			grid[r][c] = int(ch - '0')
		}
	}

	dirs := [][2]int{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}

	// Memoized count of distinct paths from (r,c) to any 9
	memo := make([][]int, rows)
	for r := range memo {
		memo[r] = make([]int, cols)
		for c := range memo[r] {
			memo[r][c] = -1
		}
	}

	var countPaths func(r, c int) int
	countPaths = func(r, c int) int {
		if grid[r][c] == 9 {
			return 1
		}
		if memo[r][c] != -1 {
			return memo[r][c]
		}
		total := 0
		for _, d := range dirs {
			nr, nc := r+d[0], c+d[1]
			if nr < 0 || nr >= rows || nc < 0 || nc >= cols {
				continue
			}
			if grid[nr][nc] == grid[r][c]+1 {
				total += countPaths(nr, nc)
			}
		}
		memo[r][c] = total
		return total
	}

	sum := 0
	for r := 0; r < rows; r++ {
		for c := 0; c < cols; c++ {
			if grid[r][c] == 0 {
				sum += countPaths(r, c)
			}
		}
	}

	fmt.Println(sum)
}
