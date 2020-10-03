# bladerunner-desktop

Console utility to find and delete file clones based on file hashes.

# Usage
Run `bladerunner` command with arguments

## Flatten
Copies all unique files to output directory.
```shell script
Options:
  -din, --directory-in DIRECTORY   Path to root directory of input
  -dout, --directory-out DIRECTORY
                                   Path to output directory
  -ns, --naming-strategy [MODIFIED_DATE|PHOTO_TAKEN]
                                   Naming strategy for created files
  -o, --out FILE                   Path to output file
  -s, --silent                     Do not log activity
  -h, --help                       Show this message and exit
```
### Naming strategy
Files named using its date and UUID.
Date is calculated differently for different naming strategies provided. 
- `MODIFIED_DATE` obtains file last modified date. `MODIFIED_DATE` is default fallback behaviour.
- `PHOTO_TAKEN` obtains photo taken date from file EXIF if possible, uses fallback otherwise.

## Clean
Deletes all non-unique files from given directory.
```shell script
Usage: java -jar bladerunner clean [OPTIONS]

Options:
  -din, --directory-in DIRECTORY  Path to root directory of input
  -o, --out FILE                  Path to output file
  -s, --silent                    Do not log activity
  -h, --help                      Show this message and exit
```

## Find
Prints information if it's clone or not about all files in given directory.
```shell script
Usage: java -jar bladerunner find [OPTIONS]

Options:
  -din, --directory-in DIRECTORY  Path to root directory of input
  -o, --out FILE                  Path to output file
  -h, --help                      Show this message and exit
```
