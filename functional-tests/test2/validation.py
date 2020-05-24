import filecmp
import sys
import json
import argparse

parser = argparse.ArgumentParser(description='Process some integers.')

parser.add_argument("expected", type=str, help="File containing expected output.")
parser.add_argument("output", type=str, help="File containing actual output.")

args = parser.parse_args()

# access automatically parsed arguments
expected_file=args.expected
output_file=args.output

import rdflib.compare

with open(expected_file) as f:
    expected_lines = f.readlines()

with open(output_file) as f:
    output_lines = f.readlines()

match = expected_lines[1] == output_lines[1]

assert match, "Actual output doesn't match the expected output."

print("success", end="")
