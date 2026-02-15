package main

import (
	"bufio"
	"fmt"
	"os"
)

// Direction vectors: up, right, down, left
var dx = [4]int{-1, 0, 1, 0}
var dy = [4]int{0, 1, 0, -1}

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: go run solution.go <input_file>")
		os.Exit(1)
	}

	filename := os.Args[1]
	file, err := os.Open(filename)
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error opening file: %v\n", err)
		os.Exit(1)
	}
	defer file.Close()

	var grid [][]byte
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		grid = append(grid, []byte(scanner.Text()))
	}

	rows := len(grid)
	cols := len(grid[0])

	// Find the guard's starting position and direction
	var startR, startC, dir int
	for r := 0; r < rows; r++ {
		for c := 0; c < cols; c++ {
			switch grid[r][c] {
			case '^':
				startR, startC, dir = r, c, 0
			case '>':
				startR, startC, dir = r, c, 1
			case 'v':
				startR, startC, dir = r, c, 2
			case '<':
				startR, startC, dir = r, c, 3
			}
		}
	}

	visited := make(map[[2]int]bool)
	r, c := startR, startC
	visited[[2]int{r, c}] = true

	for {
		nr, nc := r+dx[dir], c+dy[dir]

		// Check if the guard leaves the map
		if nr < 0 || nr >= rows || nc < 0 || nc >= cols {
			break
		}

		if grid[nr][nc] == '#' {
			// Turn right 90 degrees
			dir = (dir + 1) % 4
		} else {
			// Step forward
			r, c = nr, nc
			visited[[2]int{r, c}] = true
		}
	}

	fmt.Println(len(visited))
}
