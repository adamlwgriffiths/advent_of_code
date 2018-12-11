from __future__ import print_function
from itertools import groupby, combinations, starmap

def step_one(input):
    count = lambda s: set([len(list(v)) for _, v in groupby(sorted(s))])
    counts = list(map(count, input))
    repeats = lambda c: list(filter(lambda s: c in s, counts))
    doubles = len(repeats(2))
    triples = len(repeats(3))
    return doubles * triples

def step_two(input):
    def distance(s1, s2):
        return len(list(filter(lambda v: v[0] != v[1], zip(s1, s2))))
    def common(s1, s2):
        common_chars = list(filter(lambda v: v[0] == v[1], zip(s1, s2)))
        return ''.join(list(zip(*common_chars))[0])
    match = list(filter(lambda s: distance(*s) == 1, combinations(input, 2)))[0]
    return common(*match)

def read_data():
    with open('data', 'r') as f:
        return f.read().split('\n')

input = read_data()
# 5727
print(step_one(input))
# uwfmdjxyxlbgnrotcfpvswaqh
print(step_two(input))
