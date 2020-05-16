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

match = filecmp.cmp(expected_file, output_file)

assert match, "Actual output doesn't match the expected output."

print("success", end="")
