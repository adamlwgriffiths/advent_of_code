from __future__ import print_function
from functools import reduce
from itertools import cycle, repeat, accumulate
from operator import add

def part_one(input):
    return reduce(lambda x, y: x + y, input)

def part_two(input):
    previous = set()
    for freq in accumulate(cycle(input), add):
        if freq in previous:
            return freq
        previous.add(freq)

def read_data():
    with open('data', 'r') as f:
        data = f.readlines()
    return list(map(int, data))

input = read_data()
# 486
print(part_one(input))
# 69285
print(part_two(input))
