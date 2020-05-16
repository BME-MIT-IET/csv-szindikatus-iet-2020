import json

class Autotester:

    def __init__(self, test_root):
        self.test_root = test_root

    def execute(self, test_dir):
        print(f"Executing test contained in directory {test_dir}")
        
        test_location = os.path.join(self.test_root, test_dir)
        
        try:
            with open(os.path.join(test_location, "metadata.json")) as meta_file:
                meta = json.load(meta_file)
                print("Test metadata:")
                print(json.dumps(meta, indent=2))

        except:
            print(f"Test directory {test_location} contains no 'metadata.json' file. Ignoring directory.")

if __name__ == "__main__":

    import argparse # automatic cmd line argument parsing
    import os # file manipulation

    parser = argparse.ArgumentParser(description='Process some integers.')

    parser.add_argument("--test-dir", "-t", type=str, help="Directory containing the test. Path must be relative to the test root.")

    parser.add_argument("--test-root", "-r", type=str, help="Test root if different from where the script is being executed.")

    args = parser.parse_args()

    # access automatically parsed arguments
    test_dir = args.test_dir
    test_root = args.test_root or os.getcwd()

    if not os.path.isabs(test_root):
        test_root = os.path.join(os.getcwd(), test_root)

    tester = Autotester(test_root)

    if test_dir is not None:
        tester.execute(test_dir)
    else:
        # os.walk is recursive, next prevents accessing subdirectories
        _, directories, _ = next(os.walk(test_root))
        for dir in directories:
            tester.execute(dir)

