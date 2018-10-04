# Wissen

Wissen (Wise / German for Knowledge) - Run a self-hosted copy of a
centralized documentation store.

Aims to be a glomming of documentation from various sources to allow
strong querying of the documentation sources with something powerful
like SQL.

Will provide various interfaces for pulling in documentation from
another source and displaying it in the system SQL.

Then, various output formats can be derived from querying a database,
vs complex parsing of archaic ad-hoc source files.

## Overview

### General
#### Abstract
The intent is to index a lot of docs.

#### Why do this?
I feel that having so many doc systems is superflous, to the point
that almost no developers spend adequate time on reading
documentation, and instead opt for short blog posts etc.

#### References
https://github.com/ahungry/wissen

## Installation

Just clone the repo and type: `./bootstrap`

## Usage

Check the various available commands via: `./bin/wissen` after installing.

```sh
Usage: wissen [-h | --help] <command> [<args>]

These are common wissen commands used in various situations:

    help        Usage: "wissen help" to list this help document.
    ls          Usage: "wissen ls" to list docs.
    add         Usage: "wissen add <path> <label> <summary>" to add a doc, where path follows the form: system.subject.topic.doc
    import      Usage: "wissen import <system> <markdown-file>" to import as a doc under <system>.
    dir_import  Usage: "wissen dir_import <dir-path> [<max-depth>=2]" to import each markdown file in each sub-directory in <dir-path> as a doc under <dir-path> system.
    apropos     Usage: "wissen apropos <term>" to search all docs for the term.
```

### Sample: Adding an entry via the CLI directly

You can add an entry as such:

```sh
./bin/wissen add my_proj.usage.testing.run 'Running the Tests' 'To run the test suite, just type "Make test"'
```

Then you can search on that entry with `ls` or `apropos` as such:

```sh
./bin/wissen ls test
```

Which will print a result similar to this:

```sh
system      subject     topic       title       doc
--------------------------------------------------------------------------------------------
my_proj     usage       testing     Running th  To run the test suite, just type "Make test"
```

## License

Copyright © 2018 Matthew Carter <m@ahungry.com>

Distributed under the GNU General Public License either version 3.0 or (at
your option) any later version.
