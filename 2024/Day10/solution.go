package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: go run solution.go <input_file>")
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
	total := 0

	for r := 0; r < rows; r++ {
		for c := 0; c < cols; c++ {
			if grid[r][c] != 0 {
				continue
			}
			// BFS/DFS from this trailhead to find all reachable 9s
			reachable := map[[2]int]bool{}
			stack := [][2]int{{r, c}}
			visited := map[[2]int]bool{{r, c}: true}

			for len(stack) > 0 {
				cur := stack[len(stack)-1]
				stack = stack[:len(stack)-1]

				for _, d := range dirs {
					nr, nc := cur[0]+d[0], cur[1]+d[1]
					if nr < 0 || nr >= rows || nc < 0 || nc >= cols {
						continue
					}
					if grid[nr][nc] != grid[cur[0]][cur[1]]+1 {
						continue
					}
					key := [2]int{nr, nc}
					if visited[key] {
						continue
					}
					visited[key] = true
					if grid[nr][nc] == 9 {
						reachable[key] = true
					} else {
						stack = append(stack, key)
					}
				}
			}
			total += len(reachable)
		}
	}

	fmt.Println(total)
}
