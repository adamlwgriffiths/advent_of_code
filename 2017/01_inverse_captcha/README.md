# 01 Inverse Captcha

Solves [Advent of Code 2017 #1](https://adventofcode.com/2017/day/1)

## Pseudo-code

    take the captcha as a string (it's too big to manually munge)
    convert to a vector of integers
    for each value
      get the peer at index + <offset>
      if values are the same
        add the first value
        otherwise add 0
    repeat
