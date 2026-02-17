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
	file, err := os.Open(filename)
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error opening file: %v\n", err)
		os.Exit(1)
	}
	defer file.Close()

	total := 0
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		if line == "" {
			continue
		}

		parts := strings.SplitN(line, ": ", 2)
		target, _ := strconv.Atoi(parts[0])
		numStrs := strings.Fields(parts[1])

		nums := make([]int, len(numStrs))
		for i, s := range numStrs {
			nums[i], _ = strconv.Atoi(s)
		}

		if canMake(target, nums[0], nums[1:]) {
			total += target
		}
	}

	fmt.Println(total)
}

// canMake checks whether we can reach target by inserting + or * between
// the remaining numbers, evaluating strictly left-to-right.
func canMake(target, current int, nums []int) bool {
	if len(nums) == 0 {
		return current == target
	}
	// Early exit: with only + and *, current can never decrease
	if current > target {
		return false
	}
	return canMake(target, current+nums[0], nums[1:]) ||
		canMake(target, current*nums[0], nums[1:])
}
