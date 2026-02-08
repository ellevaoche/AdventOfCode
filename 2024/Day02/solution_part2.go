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

	reports, err := parseInput(filename)
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error reading file: %v\n", err)
		os.Exit(1)
	}

	result := solve(reports)
	fmt.Printf("Safe reports: %d\n", result)
}

// parseInput reads the input file and returns a slice of reports
func parseInput(filename string) ([][]int, error) {
	file, err := os.Open(filename)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	var reports [][]int
	scanner := bufio.NewScanner(file)

	for scanner.Scan() {
		line := strings.TrimSpace(scanner.Text())
		if line == "" {
			continue
		}

		fields := strings.Fields(line)
		var levels []int
		for _, f := range fields {
			n, err := strconv.Atoi(f)
			if err != nil {
				continue
			}
			levels = append(levels, n)
		}
		if len(levels) > 0 {
			reports = append(reports, levels)
		}
	}

	return reports, scanner.Err()
}

// solve counts how many reports are safe (with Problem Dampener)
func solve(reports [][]int) int {
	count := 0
	for _, report := range reports {
		if isSafeWithDampener(report) {
			count++
		}
	}
	return count
}

// isSafeWithDampener checks if report is safe, or becomes safe by removing one level
func isSafeWithDampener(levels []int) bool {
	// First check if already safe
	if isSafe(levels) {
		return true
	}

	// Try removing each level one at a time
	for skip := 0; skip < len(levels); skip++ {
		modified := make([]int, 0, len(levels)-1)
		for i, v := range levels {
			if i != skip {
				modified = append(modified, v)
			}
		}
		if isSafe(modified) {
			return true
		}
	}

	return false
}

// isSafe checks if a report meets the safety criteria
func isSafe(levels []int) bool {
	if len(levels) < 2 {
		return true
	}

	increasing := levels[1] > levels[0]

	for i := 1; i < len(levels); i++ {
		diff := levels[i] - levels[i-1]

		if increasing && diff <= 0 {
			return false
		}
		if !increasing && diff >= 0 {
			return false
		}

		absDiff := diff
		if absDiff < 0 {
			absDiff = -absDiff
		}
		if absDiff < 1 || absDiff > 3 {
			return false
		}
	}

	return true
}
