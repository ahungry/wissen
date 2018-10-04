all: wissen.db

wissen.db:
	cat schema.sql | sqlite3 wissen.db

test: wissen.db
	@test x"wissen" == x`sqlite3 $< "SELECT system_name FROM system WHERE system_name = 'wissen'"`
	$(info And now, a sample of how to add a doc from the CLI using this tool!)
	./bin/wissen wissen.cli.usage.add_doc 'Adding a document' 'To add a document, just run: ./bin/wissen system.subject.topic.doc_key "Doc Label" "Document description", where arg1 is a dot separated path of your document nesting for indexing in the hiearchy, arg2 ("Doc Label") is your human readable label for this index item, and arg3 ("Document description") is the description of your document in a text based format.'

clean:
	@-rm -f wissen.db
