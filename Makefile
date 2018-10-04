all: wissen.db

wissen.db:
	cat schema.sql | sqlite3 wissen.db

clean:
	@-rm -f wissen.db
