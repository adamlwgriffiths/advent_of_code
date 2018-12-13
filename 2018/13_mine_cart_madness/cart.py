from __future__ import print_function
import numpy as np
from rules import rules, all_cart_symbols

def state_string(state):
    printable = np.where(state == None, ' ', state)
    return '\n'.join([''.join(row) for row in printable])

def parse_track(inital):
    def replace_carts(state):
        # replace carts with underlying track
        track = np.copy(state)
        track = np.where(np.isin(track, ['>', '<']), '-', track)
        track = np.where(np.isin(track, ['^', 'v']), '|', track)
        return track
    def carts_only(state):
        # replace track with None, keep carts
        carts = np.where(np.isin(state, ['>', '<', '^', 'v']), state, None)
        return carts

    track = replace_carts(inital)
    state = carts_only(inital)
    return track, state

def centre(track):
    return track[1,1]

def process_block(state, track):
    # get the rules for the centre track cell
    # then find a rule that matches it and returns a result to apply
    # use the rule match as a mask and see the state is equal
    for rule in rules[centre(track)]:
        match = np.where(state == rule.match, state, None)
        if np.all(match == rule.match):
            return rule.result
    return None

def step(track, state):
    cart_chars = all_cart_symbols()
    # pad to support emulating negative indices
    # next state is always taken from a clean track
    pad = lambda track: np.pad(track, 1, mode='wrap')
    remove_padding = lambda track: track[1:-1, 1:-1]
    xy_slice = lambda x, y: (slice(y-1,y+2), slice(x-1,x+2))
    _track, _state = pad(track), pad(state)
    next = pad(np.full_like(state, None))

    # iterate through our grid igoring the wrapped borders
    # and perform a cellular automata on each 3x3 block
    # the result of which is a diff which is applied to the resulting grid
    for y in range(1, _track.shape[0] - 1):
        for x in range(1, _state.shape[1] - 1):
            _slice = xy_slice(x, y)
            # don't run the automata if there are no carts nearby
            if not centre(_state[_slice]) in cart_chars:
                continue
            delta = process_block(_state[_slice], _track[_slice])
            if delta is not None:
                next[_slice] = np.where(delta != None, delta, next[_slice])
    return remove_padding(next)

def count_crashes(state):
    return np.count_nonzero(state == 'X')

def load_map(filename):
    with open(filename, 'r') as f:
        # split each line, then split each character
        data = [list(row) for row in f.read().split('\n') if row]
        np_data = np.array(data, dtype=str)
        return np_data

def step_one(map):
    track, state = parse_track(map)
    view = np.where(state != None, state, track)
    frame = 0
    while not count_crashes(state):
        print(frame)
        frame += 1
        state = step(track, state)
        view = np.where(state != None, state, track)
        crashes = count_crashes(state)
        if crashes:
            index = np.where(view=='X')
            y, x = index[0][0], index[1][0]
            return view, x, y

def step_two(map):
    pass

def run():
    filename = 'data'
    #filename = 'test_data'
    map = load_map(filename)
    view, x, y = step_one(map)
    print(state_string(view))
    print(x, y)
    step_two(map)

if __name__ == '__main__':
    run()
