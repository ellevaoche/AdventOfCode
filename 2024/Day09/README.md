# Day 9: Disk Fragmenter

Compact files on a disk by rearranging blocks to eliminate free-space gaps, then compute a filesystem checksum.

## Part 1

**Task:** Move individual file blocks from the end of the disk to the leftmost free space, then calculate the checksum.

A dense disk map alternates file lengths and free-space lengths. Each file has an ID based on its original order. After expanding, repeatedly swap the rightmost file block with the leftmost free block until no gaps remain.

```go
// Two-pointer compaction
left, right := 0, len(blocks)-1
for left < right {
    if blocks[left] != -1 { left++; continue }
    if blocks[right] == -1 { right--; continue }
    blocks[left], blocks[right] = blocks[right], blocks[left]
}
```

Example: `2333133121414131402` → checksum **1928**

## Part 2

**Task:** Move whole files (not individual blocks) to the leftmost free span large enough to fit them, processing files from highest ID to lowest.

Instead of block-level compaction, track file and free-space spans. For each file (descending ID), find the leftmost free span to its left that can fit it entirely, and move it there.

```go
// Move whole files from highest ID down
for id := len(files) - 1; id >= 0; id-- {
    for j := range frees {
        if frees[j].start >= files[id].start { break }
        if frees[j].length >= files[id].length {
            files[id].start = frees[j].start
            frees[j].start += files[id].length
            frees[j].length -= files[id].length
            break
        }
    }
}
```

Example: `2333133121414131402` → checksum **2858**

## Usage

```bash
go run solution.go input.txt        # Part 1
go run solution_part2.go input.txt  # Part 2
```

[Link to Problem](https://adventofcode.com/2024/day/9)
