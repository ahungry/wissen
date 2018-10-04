all: wissen.db

wissen.db:
	@cat schema.sql | sqlite3 wissen.db >/dev/null
	 ./bin/wissen add wissen.cli.usage.ls 'ls' 'Usage: "wissen ls" to list docs.'
	 ./bin/wissen add wissen.cli.usage.add 'add' 'Usage: "wissen add <path> <label> <summary>" to add a doc, where path follows the form: system.subject.topic.doc'

test: wissen.db
	@test x"wissen" == x`sqlite3 $< "SELECT system_name FROM system WHERE system_name = 'wissen'"`

clean:
	@-rm -f wissen.db
