from __future__ import print_function
import numpy as np

def parse_claim(raw_claim):
    # #1 @ 604,100: 17x27
    s = raw_claim.split()
    i = s[0].replace('#', '')
    x, y = s[2].replace(':', '').split(',')
    w, h = s[3].split('x')
    i, x, y, w, h = list(map(int, [i, x, y, w, h]))
    return i, x, y, w, h

def max_dimensions(claims):
    right = lambda d: d[1] + d[3]
    bottom = lambda d: d[2] + d[4]
    return max(map(right, claims)), max(map(bottom, claims))

def claim_slice(c):
    _, x1, y1, w, h = c
    x2, y2 = x1 + w, y1 + h
    return slice(x1,x2), slice(y1,y2)

def apply_claims(claims):
    area = np.zeros(max_dimensions(claims), dtype=int)
    for claim in claims:
        area[claim_slice(claim)] += 1
    return area

def step_one(input):
    claims = list(map(parse_claim, input))
    area = apply_claims(claims)
    return np.count_nonzero(area > 1)

def step_two(input):
    claims = list(map(parse_claim, input))
    area = apply_claims(claims)
    for claim in claims:
        if np.all(area[claim_slice(claim)] == 1):
            return claim[0]

def read_data():
    with open('data', 'r') as f:
        data = f.read().split('\n')
        data = list(filter(None, data))
        return data

input = read_data()
# 115348
print(step_one(input))
# 188
print(step_two(input))
