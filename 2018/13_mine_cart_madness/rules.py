import numpy as np
from itertools import chain, product
from collections import namedtuple

Rule = namedtuple('Rule', 'match result')

# cart states are (left, right, up, down respectively)
# next left:        <>^v
# next straight:    {}*,
# next right:       []`.

cart_states = {
    '^': "^`'",
    '>': ">}]",
    'v': "v,.",
    '<': "<{[",
}

def all_cart_symbols():
    return list(''.join(cart_states.values()))

def left_turning(char):
    return cart_states[char][0]

def straight_turning(char):
    return cart_states[char][1]

def right_turning(char):
    return cart_states[char][2]

def cell_centre(char):
    return np.array([
        [None, None, None],
        [None, char, None],
        [None, None, None]
    ])

def cell_up(char):
    return np.array([
        [None, char, None],
        [None, None, None],
        [None, None, None]
    ])
def cell_right(char):
    return np.array([
        [None, None, None],
        [None, None, char],
        [None, None, None]
    ])
def cell_down(char):
    return np.array([
        [None, None, None],
        [None, None, None],
        [None, char, None]
    ])
def cell_left(char):
    return np.array([
        [None, None, None],
        [char, None, None],
        [None, None, None]
    ])

def all_states(char_1, result_fn, char_2=None):
    char_2 = char_2 or char_1
    return [
        Rule(cell_centre(_char_1), result_fn(_char_2))
        for _char_1, _char_2 in zip(cart_states[char_1], cart_states[char_2])
    ]

def merge(state1, state2):
    return np.where(state1 != None, state1, state2)

def crash(char_1, match_fn):
    return [
        Rule(merge(cell_centre(_char1), match_fn(_char2)), cell_centre('X'))
        for _char1, _char2 in product(cart_states[char_1], all_cart_symbols())
    ]

def specific_crash(char_1, match_fn):
    return [
        Rule(merge(cell_centre(_char1), match_fn(_char2)), cell_centre('X'))
        for _char1, _char2 in product([char_1], all_cart_symbols())
    ]


rules = {
    ' ': [],
    '-': [x for x in chain(
        crash('>', cell_right),
        crash('>', cell_left),
        all_states('>', cell_right),
        all_states('<', cell_left),
    )],
    '|': [x for x in chain(
        crash('v', cell_down),
        crash('^', cell_up),
        all_states('^', cell_up),
        all_states('v', cell_down),
    )],
    '/': [x for x in chain(
        crash('>', cell_up),
        crash('<', cell_down),
        crash('^', cell_right),
        crash('v', cell_left),
        all_states('^', cell_right, '>'),
        all_states('>', cell_up, '^'),
        all_states('v', cell_left, '<'),
        all_states('<', cell_centre, 'v'),
    )],
    '\\': [x for x in chain(
        crash('>', cell_down),
        crash('<', cell_up),
        crash('^', cell_left),
        crash('v', cell_right),
        all_states('^', cell_left, '<'),
        all_states('>', cell_down, 'v'),
        all_states('v', cell_right, '>'),
        all_states('<', cell_up, '^'),
    )],
    '+': [x for x in chain(
        # crashes
        specific_crash(left_turning('^'), cell_left),
        specific_crash(left_turning('>'), cell_up),
        specific_crash(left_turning('v'), cell_right),
        specific_crash(left_turning('<'), cell_up),
        specific_crash(straight_turning('^'), cell_up),
        specific_crash(straight_turning('>'), cell_right),
        specific_crash(straight_turning('v'), cell_down),
        specific_crash(straight_turning('<'), cell_left),
        specific_crash(right_turning('^'), cell_right),
        specific_crash(right_turning('>'), cell_down),
        specific_crash(right_turning('v'), cell_left),
        specific_crash(right_turning('<'), cell_up),
        # cart state changes
        [
            # left turning > straight
            Rule(cell_centre(left_turning('^')), cell_left(straight_turning('<'))),
            Rule(cell_centre(left_turning('>')), cell_up(straight_turning('^'))),
            Rule(cell_centre(left_turning('v')), cell_right(straight_turning('>'))),
            Rule(cell_centre(left_turning('<')), cell_down(straight_turning('v'))),
            # straight > right turning
            Rule(cell_centre(straight_turning('^')), cell_up(right_turning('^'))),
            Rule(cell_centre(straight_turning('>')), cell_right(right_turning('>'))),
            Rule(cell_centre(straight_turning('v')), cell_down(right_turning('v'))),
            Rule(cell_centre(straight_turning('<')), cell_left(right_turning('<'))),
            # right turning > left turning
            Rule(cell_centre(right_turning('^')), cell_right(left_turning('>'))),
            Rule(cell_centre(right_turning('>')), cell_down(left_turning('v'))),
            Rule(cell_centre(right_turning('v')), cell_left(left_turning('<'))),
            Rule(cell_centre(right_turning('<')), cell_up(left_turning('^'))),
        ]
    )],
}
