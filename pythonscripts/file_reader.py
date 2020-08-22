import os

def read_file(input_file):
    total_thes = 0
    if os.path.isfile(input_file):
        file_handler = open(input_file, "r")
        contents = file_handler.readlines()
        for line in contents:
            total_thes += line.count("the")
        return total_thes
        
def main():
    input_file = "/usr/local/etc/telegraf.conf"
    print(read_file(input_file))

if __name__ == "__main__":
    main()
