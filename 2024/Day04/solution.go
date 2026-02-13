package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: go run solution.go <input_file>")
		os.Exit(1)
	}

	file, err := os.Open(os.Args[1])
	if err != nil {
		fmt.Printf("Error opening file: %v\n", err)
		os.Exit(1)
	}
	defer file.Close()

	var grid []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		if line != "" {
			grid = append(grid, line)
		}
	}

	rows := len(grid)
	cols := len(grid[0])
	word := "XMAS"
	count := 0

	// 8 directions: right, left, down, up, and 4 diagonals
	dirs := [][2]int{
		{0, 1}, {0, -1}, {1, 0}, {-1, 0},
		{1, 1}, {1, -1}, {-1, 1}, {-1, -1},
	}

	for r := 0; r < rows; r++ {
		for c := 0; c < cols; c++ {
			for _, d := range dirs {
				if matches(grid, r, c, d[0], d[1], word, rows, cols) {
					count++
				}
			}
		}
	}

	fmt.Println(count)
}

func matches(grid []string, r, c, dr, dc int, word string, rows, cols int) bool {
	for i := 0; i < len(word); i++ {
		nr := r + i*dr
		nc := c + i*dc
		if nr < 0 || nr >= rows || nc < 0 || nc >= cols {
			return false
		}
		if grid[nr][nc] != word[i] {
			return false
		}
	}
	return true
}
