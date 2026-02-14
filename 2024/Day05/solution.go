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

	file, err := os.Open(os.Args[1])
	if err != nil {
		fmt.Printf("Error opening file: %v\n", err)
		os.Exit(1)
	}
	defer file.Close()

	// Parse ordering rules and updates
	rules := make(map[[2]int]bool) // rules[{X,Y}] = true means X must come before Y
	var updates [][]int
	parsingRules := true

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		if line == "" {
			parsingRules = false
			continue
		}

		if parsingRules {
			parts := strings.Split(line, "|")
			x, _ := strconv.Atoi(parts[0])
			y, _ := strconv.Atoi(parts[1])
			rules[[2]int{x, y}] = true
		} else {
			parts := strings.Split(line, ",")
			update := make([]int, len(parts))
			for i, p := range parts {
				update[i], _ = strconv.Atoi(p)
			}
			updates = append(updates, update)
		}
	}

	sum := 0
	for _, update := range updates {
		if isCorrectOrder(update, rules) {
			mid := update[len(update)/2]
			sum += mid
		}
	}

	fmt.Println(sum)
}

// isCorrectOrder checks if the update respects all applicable ordering rules
func isCorrectOrder(update []int, rules map[[2]int]bool) bool {
	// Build position map for pages in this update
	pos := make(map[int]int)
	for i, page := range update {
		pos[page] = i
	}

	// Check all rules: if both pages are in the update, X must appear before Y
	for rule := range rules {
		posX, okX := pos[rule[0]]
		posY, okY := pos[rule[1]]
		if okX && okY && posX > posY {
			return false
		}
	}
	return true
}
