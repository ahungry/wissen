all: help

help: bin/wissen
	./bin/wissen help

bin/wissen: wissen.db
	@cp skel/wissen bin/
	@chmod +x bin/wissen
	@./bin/wissen add wissen.cli.usage.help 'help' 'Usage: "wissen help" to list this help document.'
	@./bin/wissen add wissen.cli.usage.ls 'ls' 'Usage: "wissen ls" to list docs.'
	@./bin/wissen add wissen.cli.usage.add 'add' 'Usage: "wissen add <path> <label> <summary>" to add a doc, where path follows the form: system.subject.topic.doc'
	@./bin/wissen add wissen.cli.usage.import 'import' 'Usage: "wissen import <system> <markdown-file>" to import as a doc under <system>.'
	@./bin/wissen add wissen.cli.usage.dir_import 'dir_import' 'Usage: "wissen dir_import <dir-path> [<max-depth>=2]" to import each markdown file in each sub-directory in <dir-path> as a doc under <dir-path> system.'
	@./bin/wissen add wissen.cli.usage.apropos 'apropos' 'Usage: "wissen apropos <term>" to search all docs for the term.'
	@./bin/wissen add wissen.cli.usage.gapropos 'gapropos' 'Usage: "wissen gapropos <term>" to search all docs for the term and open in web browser.'

wissen.db:
	@cat schema.sql | sqlite3 wissen.db >/dev/null

test: wissen.db
	@test x"wissen" == x`sqlite3 $< "SELECT system_name FROM system WHERE system_name = 'wissen'"`

clean:
	-rm -f wissen.db
	-rm -fr bin/*
	$(info All tidy and clean!)

.PHONY: help
