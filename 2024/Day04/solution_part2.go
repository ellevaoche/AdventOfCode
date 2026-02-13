package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: go run solution_part2.go <input_file>")
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
	count := 0

	// For each 'A', check if both diagonals form MAS (forwards or backwards)
	for r := 1; r < rows-1; r++ {
		for c := 1; c < cols-1; c++ {
			if grid[r][c] != 'A' {
				continue
			}
			// Diagonal 1: top-left to bottom-right
			d1 := string([]byte{grid[r-1][c-1], grid[r+1][c+1]})
			// Diagonal 2: top-right to bottom-left
			d2 := string([]byte{grid[r-1][c+1], grid[r+1][c-1]})

			if (d1 == "MS" || d1 == "SM") && (d2 == "MS" || d2 == "SM") {
				count++
			}
		}
	}

	fmt.Println(count)
}
