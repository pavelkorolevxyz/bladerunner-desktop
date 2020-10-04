![Java CI](https://github.com/pavelkorolevxyz/bladerunner-desktop/workflows/Java%20CI/badge.svg?branch=development)

# bladerunner-desktop

Console utility to find and delete file clones based on file hashes.

# Usage
Run `bladerunner` using Java.

```shell script
java -jar bladerunner.jar [COMMAND] [OPTIONS]
```

## Run
Copies all unique files to output directory.
```shell script
Usage: bladerunner run [OPTIONS]

Options:
  -din, --directory-in DIRECTORY   Path to root directory of input
  -dout, --directory-out DIRECTORY
                                   Path to output directory
  -ns, --naming-strategy [DEFAULT|DATE_MODIFIED|PHOTO_TAKEN]
                                   Naming strategy for created files
  -o, --out FILE                   Path to output file
  -s, --silent                     Do not log activity
  -f, --flatten                    Copy all files into out directory without
                                   saving directory tree
  -h, --help                       Show this message and exit
```

### Naming strategy
Files named using its date and UUID.
Date is calculated differently for different naming strategies provided. 
- `DEFAULT` obtains original file name. `DEFAULT` is default fallback behaviour.
- `DATE_MODIFIED` obtains file last modified date. 
- `PHOTO_TAKEN` obtains photo taken date from file EXIF if possible, uses fallback otherwise.

Random UUID string is added as file name suffix if there's file with same file name in output directory. 

## Clean
Deletes all non-unique files from given directory.
```shell script
Usage: bladerunner clean [OPTIONS]

Options:
  -din, --directory-in DIRECTORY  Path to root directory of input
  -o, --out FILE                  Path to output file
  -s, --silent                    Do not log activity
  -h, --help                      Show this message and exit
```

## Find
Prints information if it's clone or not about all files in given directory.
```shell script
Usage: bladerunner find [OPTIONS]

Options:
  -din, --directory-in DIRECTORY  Path to root directory of input
  -o, --out FILE                  Path to output file
  -h, --help                      Show this message and exit
```
