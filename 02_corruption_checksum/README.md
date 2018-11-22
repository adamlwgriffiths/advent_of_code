# 02 Corruption Checksum

Solves [Advent of Code 2017 #2](https://adventofcode.com/2017/day/2)

## Pseudo-code

    take the spreadsheet as a list of lists
    for each row
        subtract the minimum from the maximum value
    sum the values
