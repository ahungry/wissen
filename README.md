# WISSEN

Wissen (Wise / German for Knowledge) - A localized copy of a
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
    import      Usage: "wissen import <markdown-file>" to import as a doc.
    apropos     Usage: "wissen apropos <term>" to search all docs for the term.
```

## License

Copyright © 2018 Matthew Carter <m@ahungry.com>

Distributed under the GNU General Public License either version 3.0 or (at
your option) any later version.
