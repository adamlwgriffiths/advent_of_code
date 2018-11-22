# 01 Inverse Captcha

Solves [Advent of Code 2017 #1](https://adventofcode.com/2017/day/1)

## Pseudo-code

    take the captcha as a string (it's too big to manually munge)
    convert to a list of integers
    add the first number to the end of the list to enable the wrap-around check
    for each pair
      if the same
       add the first value
       otherwise add 0
    drop the front of the list
    repeat
