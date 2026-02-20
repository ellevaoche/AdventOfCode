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
	line := strings.TrimSpace(string(data))

	// Expand disk map into block representation
	// -1 means free space, >= 0 means file ID
	var blocks []int
	fileID := 0
	for i, ch := range line {
		length := int(ch - '0')
		if i%2 == 0 {
			// File block
			for j := 0; j < length; j++ {
				blocks = append(blocks, fileID)
			}
			fileID++
		} else {
			// Free space
			for j := 0; j < length; j++ {
				blocks = append(blocks, -1)
			}
		}
	}

	// Compact: move blocks from end to leftmost free space
	left := 0
	right := len(blocks) - 1
	for left < right {
		if blocks[left] != -1 {
			left++
			continue
		}
		if blocks[right] == -1 {
			right--
			continue
		}
		blocks[left], blocks[right] = blocks[right], blocks[left]
		left++
		right--
	}

	// Calculate checksum
	checksum := 0
	for i, id := range blocks {
		if id != -1 {
			checksum += i * id
		}
	}

	fmt.Println(checksum)
}
