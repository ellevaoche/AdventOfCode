package main

import (
	"bufio"
	"fmt"
	"os"
	"slices"
	"strconv"
	"strings"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: go run solution.go <input_file>")
		os.Exit(1)
	}

	filename := os.Args[1]

	left, right, err := parseInput(filename)
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error reading file: %v\n", err)
		os.Exit(1)
	}

	result := solve(left, right)
	fmt.Printf("Total Distance: %d\n", result)
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

// solve calculates total distance between sorted paired elements
func solve(left, right []int) int {
	// Create copies to avoid modifying original slices
	l := make([]int, len(left))
	r := make([]int, len(right))
	copy(l, left)
	copy(r, right)

	// Sort both lists
	slices.Sort(l)
	slices.Sort(r)

	// Calculate total distance
	total := 0
	for i := range l {
		diff := l[i] - r[i]
		if diff < 0 {
			diff = -diff
		}
		total += diff
	}

	return total
}
