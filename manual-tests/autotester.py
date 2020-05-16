import json # json parsing
from collections import namedtuple # used to convert parsed json dict to javascript-like OO json data structure
import contextlib # easily create with-able methods

class Autotester:

    def __init__(self, test_root):
        self.test_root = test_root

    # execute command at a different directory than cwd (should be used as a with block)
    @contextlib.contextmanager
    def __execute_at(self, dir):
        cwd = os.getcwd()
        os.chdir(dir)
        try:
            yield
        finally:
            os.chdir(cwd)
        
    def execute(self, test_dir):
        print(f"Executing test contained in directory {test_dir}")
        
        test_location = os.path.join(self.test_root, test_dir)
        
        with self.__execute_at(test_location):
            metadata_location = os.path.join(test_location, "metadata.json")
            if not os.path.exists(metadata_location):
                print(f"Test directory {test_location} contains no 'metadata.json' file. Ignoring directory.")
                return True

            try:
                 with open(metadata_location) as meta_file:
                     meta = json.load(meta_file, object_hook=lambda dict_: namedtuple('_', dict_.keys())(*dict_.values()))

                 print("Test metadata:")
                 print(json.dumps(meta, indent=2))

                 for role in meta.files._fields:
                     abspath = os.path.join(os.getcwd(), getattr(meta.files, role))
                     meta = meta._replace(files=meta.files._replace(**{role: abspath}))

                 print("Executing test script.")
                 test_command = meta.script.format(meta=meta)
                 os.system(test_command)

                 print("Valiating test result.")
                 val_command = meta.validation.format(meta=meta)
                 with os.popen(val_command) as val_pipe:
                     val_output = val_pipe.read().split('\n')[-1]
                
                 assert val_output == "success", "Validation process did not return 'success'"

                 return True

            except Exception as ex:
                 print(f"Test {test_location} failed, details:")
                 print(ex)

if __name__ == "__main__":

    try:
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
            success = tester.execute(test_dir)
        else:
            success = True
            # os.walk is recursive, next prevents accessing subdirectories
            _, directories, _ = next(os.walk(test_root))
            for dir in directories:
                success = success and tester.execute(dir)
    
        assert success, "There were some test failures."
        print("All tests have passed.")
        sys.exit("failure")
    except:
        sys.exit("failure")

