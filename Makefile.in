all: wissen.db

wissen.db:
	@cat schema.sql | sqlite3 wissen.db >/dev/null

test: wissen.db
	@test x"wissen" == x`sqlite3 $< "SELECT system_name FROM system WHERE system_name = 'wissen'"`
	$(info And now, a sample of how to add a doc from the CLI using this tool!)
	./bin/wissen wissen.cli.usage.add_doc 'Sample' 'So easy to add a doc via CLI!'

clean:
	@-rm -f wissen.db
