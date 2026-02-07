package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: go run solution_part2.go <input_file>")
		os.Exit(1)
	}

	filename := os.Args[1]

	left, right, err := parseInput(filename)
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error reading file: %v\n", err)
		os.Exit(1)
	}

	result := solve(left, right)
	fmt.Printf("Similarity Score: %d\n", result)
}

// parseInput reads the input file and returns two slices of integers
func parseInput(filename string) ([]int, []int, error) {
	file, err := os.Open(filename)
	if err != nil {
		return nil, nil, err
	}
	defer file.Close()

	var left, right []int
	scanner := bufio.NewScanner(file)

	for scanner.Scan() {
		line := strings.TrimSpace(scanner.Text())
		if line == "" {
			continue
		}

		fields := strings.Fields(line)
		if len(fields) != 2 {
			continue
		}

		l, err1 := strconv.Atoi(fields[0])
		r, err2 := strconv.Atoi(fields[1])
		if err1 != nil || err2 != nil {
			continue
		}

		left = append(left, l)
		right = append(right, r)
	}

	return left, right, scanner.Err()
}

// solve calculates similarity score
// Each number in left list is multiplied by how often it appears in right list
func solve(left, right []int) int {
	// Count occurrences in right list
	counts := make(map[int]int)
	for _, v := range right {
		counts[v]++
	}

	// Calculate similarity score
	score := 0
	for _, v := range left {
		score += v * counts[v]
	}

	return score
}
