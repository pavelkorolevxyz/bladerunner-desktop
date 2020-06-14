# bladerunner-desktop

Console utility to find and delete file clones.

# Usage
Run `bladerunner` command with arguments

## Flatten
Copies all unique files to output directory.
```shell script
Usage: bladerunner flatten [OPTIONS]

Options:
  -din, --directory-in PATH    Path to root directory of input
  -dout, --directory-out PATH  Path to output directory
  -s, --silent                 Do not log activity
  -h, --help                   Show this message and exit
```

## Clean
Deletes all non-unique files from given directory.
```shell script
Usage: bladerunner clean [OPTIONS]

Options:
  -d, --directory TEXT  Path to root directory
  -s, --silent          Do not log activity
  -h, --help            Show this message and exit
```

## Find
Prints information if it's clone or not about all files in given directory.
```shell script
Usage: bladerunner find [OPTIONS]

Options:
  -d, --directory TEXT  Path to root directory
  -o, --out TEXT        Path to output file
  -h, --help            Show this message and exit
```

# Naming
Files named using it's date and UUID. Date is calculated differently for different file types. For photos it's EXIF original date. Last modified date used for all other types.