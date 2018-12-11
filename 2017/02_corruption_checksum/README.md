# 02 Corruption Checksum

Solves [Advent of Code 2017 #2](https://adventofcode.com/2017/day/2)

## Pseudo-code

Part 1

    take the spreadsheet as a list of lists
    for each row
        subtract min from max of row
    sum the row calculations

Part 2
    take the spreadsheet as a list of lists
    for each row
        create paired combinations of row values
        reverse sort and divide each pair
        keep the first pair that is a whole number
    sum the row calculations
