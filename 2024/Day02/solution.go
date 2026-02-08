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
		fmt.Println("Usage: go run solution.go <input_file>")
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

// parseInput reads the input file and returns a slice of reports (each report is a slice of levels)
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

// solve counts how many reports are safe
func solve(reports [][]int) int {
	count := 0
	for _, report := range reports {
		if isSafe(report) {
			count++
		}
	}
	return count
}

// isSafe checks if a report meets the safety criteria:
// - All levels are either increasing or decreasing
// - Adjacent levels differ by at least 1 and at most 3
func isSafe(levels []int) bool {
	if len(levels) < 2 {
		return true
	}

	// Determine direction from first pair
	increasing := levels[1] > levels[0]

	for i := 1; i < len(levels); i++ {
		diff := levels[i] - levels[i-1]

		// Check direction consistency
		if increasing && diff <= 0 {
			return false
		}
		if !increasing && diff >= 0 {
			return false
		}

		// Check difference magnitude (1-3)
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
