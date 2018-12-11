from __future__ import print_function
from functools import partial
import re
import numpy as np
from dateutil.parser import *

def parse_entry(s):
    m = re.match(r'\[(?P<time>[^\]]+)\] (?P<event>.*)', s, flags=re.I)
    return parse(m.group('time')), m.group('event').lower()

class Guard(object):
    def __init__(self, id):
        self.id = id
        self.data = np.zeros((60), int)

    def apply_sleep(self, start, end):
        for minute in range(start, end):
            self.data[minute] += 1

    def slept_minutes(self):
        return np.sum(self.data)

    def highest_frequency(self):
        return self.data[self.most_frequent_minute()]

    def most_frequent_minute(self):
        return np.argmax(self.data)

class GuardState(object):
    @staticmethod
    def can_process(entry):
        return entry[1].startswith('guard')

    @staticmethod
    def process(data, fn, entry):
        def parse_guard(event):
            m = re.match(r'Guard #(?P<id>\d+) begins shift', event, flags=re.I)
            return int(m.group('id'))

        time, event = entry
        id = parse_guard(event)
        return partial(apply_sleep, data, id)

class SleepsState(object):
    @staticmethod
    def can_process(entry):
        return entry[1].startswith('falls')

    @staticmethod
    def process(data, fn, entry):
        start = entry[0]
        return partial(fn, start)

class WakesState(object):
    @staticmethod
    def can_process(entry):
        return entry[1].startswith('wakes')

    @staticmethod
    def process(data, fn, entry):
        end = entry[0]
        return fn(end)

states = [
    (GuardState.can_process, GuardState.process),
    (SleepsState.can_process, SleepsState.process),
    (WakesState.can_process, WakesState.process),
]

def run_state(data, fn, event):
    for entry, state in states:
        if entry(event):
            return state(data, fn, event)

def apply_sleep(data, id, start, end):
    data[id] = data.get(id) or Guard(id)
    data[id].apply_sleep(start, end)
    return partial(apply_sleep, data, id)

def process_guards():
    # parse events
    # keep minutes
    events = list(map(parse_entry, sorted(input)))
    events = list(map(lambda event: (event[0].minute, event[1]), events))

    # run a hilarious state machine that slowly builds up a partial function
    # because reasons
    fn = None
    data = {}
    for event in events:
        fn = run_state(data, fn, event)
    return data

def step_one(input):
    data = process_guards()
    guard = sorted([guard for id, guard in data.items()], key=lambda g: g.slept_minutes())[-1]
    return guard.id * guard.most_frequent_minute()

def step_two(input):
    data = process_guards()
    guard = sorted([guard for id, guard in data.items()], key=lambda g: g.highest_frequency())[-1]
    return guard.id * guard.most_frequent_minute()

def read_data():
    with open('data', 'r') as f:
        data = f.read().split('\n')
        data = list(filter(None, data))
        return data

input = read_data()
# 140932
print(step_one(input))
# 51232
print(step_two(input))
