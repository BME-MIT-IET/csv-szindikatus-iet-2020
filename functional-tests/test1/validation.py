import argparse
import rdflib.compare

parser = argparse.ArgumentParser(description='Process some integers.')

parser.add_argument("expected", type=str, help="File containing expected output.")
parser.add_argument("output", type=str, help="File containing actual output.")

args = parser.parse_args()

# access automatically parsed arguments
expected_file=args.expected
output_file=args.output

expected_graph = rdflib.Graph().parse(expected_file, format="ttl")
output_graph = rdflib.Graph().parse(output_file, format="ttl")

match = rdflib.compare.isomorphic(expected_graph, output_graph)

assert match, "Actual output doesn't match the expected output."

print("success", end="")
