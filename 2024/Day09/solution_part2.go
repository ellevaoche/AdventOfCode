package main

import (
	"fmt"
	"os"
	"strings"
)

type span struct {
	start, length int
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
	line := strings.TrimSpace(string(data))

	// Parse disk map into file spans and free spans
	var files []span // files[id] = {start, length}
	var frees []span // free space spans
	pos := 0
	for i, ch := range line {
		length := int(ch - '0')
		if i%2 == 0 {
			files = append(files, span{pos, length})
		} else {
			if length > 0 {
				frees = append(frees, span{pos, length})
			}
		}
		pos += length
	}

	// Move whole files from highest ID to lowest
	for id := len(files) - 1; id >= 0; id-- {
		f := files[id]
		// Find leftmost free span that can fit this file and is to the left
		for j := 0; j < len(frees); j++ {
			if frees[j].start >= f.start {
				break
			}
			if frees[j].length >= f.length {
				// Move file here
				files[id].start = frees[j].start
				frees[j].start += f.length
				frees[j].length -= f.length
				break
			}
		}
	}

	// Calculate checksum
	checksum := 0
	for id, f := range files {
		for i := 0; i < f.length; i++ {
			checksum += (f.start + i) * id
		}
	}

	fmt.Println(checksum)
}
