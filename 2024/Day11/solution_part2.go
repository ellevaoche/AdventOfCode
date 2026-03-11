package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func blink(counts map[int]int) map[int]int {
	next := map[int]int{}
	for stone, count := range counts {
		if stone == 0 {
			next[1] += count
		} else if s := strconv.Itoa(stone); len(s)%2 == 0 {
			mid := len(s) / 2
			left, _ := strconv.Atoi(s[:mid])
			right, _ := strconv.Atoi(s[mid:])
			next[left] += count
			next[right] += count
		} else {
			next[stone*2024] += count
		}
	}
	return next
}

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: go run solution_part2.go <input_file>")
		os.Exit(1)
	}

	data, err := os.ReadFile(os.Args[1])
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error reading file: %v\n", err)
		os.Exit(1)
	}

	parts := strings.Fields(strings.TrimSpace(string(data)))
	counts := map[int]int{}
	for _, p := range parts {
		n, _ := strconv.Atoi(p)
		counts[n]++
	}

	for i := 0; i < 75; i++ {
		counts = blink(counts)
	}

	total := 0
	for _, c := range counts {
		total += c
	}
	fmt.Println(total)
}
