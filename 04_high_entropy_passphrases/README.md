# 04 High-Entropy Passphrases

Solves [Advent of Code 2017 #4](https://adventofcode.com/2017/day/4)

## Pseudo-code

Part 1

    for each passphrase
        split passphrase
        convert each word to set
        see if the set length is the same as the split passphrase

Part 2

    for each passphrase
        split passphrase
        sort each word
        run step 1 on joined passphrase
