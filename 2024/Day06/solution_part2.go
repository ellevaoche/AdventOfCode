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
		fmt.Println("Usage: go run solution_part2.go <input_file>")
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
	var startR, startC, startDir int
	for r := 0; r < rows; r++ {
		for c := 0; c < cols; c++ {
			switch grid[r][c] {
			case '^':
				startR, startC, startDir = r, c, 0
			case '>':
				startR, startC, startDir = r, c, 1
			case 'v':
				startR, startC, startDir = r, c, 2
			case '<':
				startR, startC, startDir = r, c, 3
			}
		}
	}

	// First, find the guard's original patrol path (only those cells are worth testing)
	originalPath := getPatrolPath(grid, rows, cols, startR, startC, startDir)

	// For each cell on the original path (except the start), try placing an obstruction
	count := 0
	for pos := range originalPath {
		r, c := pos[0], pos[1]
		if r == startR && c == startC {
			continue
		}
		if grid[r][c] == '#' {
			continue
		}

		// Temporarily place obstruction
		grid[r][c] = '#'
		if causesLoop(grid, rows, cols, startR, startC, startDir) {
			count++
		}
		// Remove obstruction
		grid[r][c] = '.'
	}

	fmt.Println(count)
}

// getPatrolPath returns the set of positions the guard visits on the original grid.
func getPatrolPath(grid [][]byte, rows, cols, startR, startC, dir int) map[[2]int]bool {
	visited := make(map[[2]int]bool)
	r, c := startR, startC
	visited[[2]int{r, c}] = true

	for {
		nr, nc := r+dx[dir], c+dy[dir]
		if nr < 0 || nr >= rows || nc < 0 || nc >= cols {
			break
		}
		if grid[nr][nc] == '#' {
			dir = (dir + 1) % 4
		} else {
			r, c = nr, nc
			visited[[2]int{r, c}] = true
		}
	}
	return visited
}

// causesLoop simulates the guard and returns true if the guard enters an infinite loop.
func causesLoop(grid [][]byte, rows, cols, startR, startC, dir int) bool {
	// Use a set of (row, col, direction) states to detect loops
	type state struct {
		r, c, dir int
	}
	seen := make(map[state]bool)
	r, c := startR, startC

	for {
		s := state{r, c, dir}
		if seen[s] {
			return true
		}
		seen[s] = true

		nr, nc := r+dx[dir], c+dy[dir]
		if nr < 0 || nr >= rows || nc < 0 || nc >= cols {
			return false
		}
		if grid[nr][nc] == '#' {
			dir = (dir + 1) % 4
		} else {
			r, c = nr, nc
		}
	}
}
