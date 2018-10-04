-- -*- mode: sql; sql-product: sqlite; -*-

-- We want to build a structure to contain documentation for all possible
-- systems in a queryable database.  Why?  Because then we can do something
-- such as write analysis for common terms, as well as leverage a highly
-- optimized search system, vs making some ad-hoc parser to try to pull out
-- relevant search terms.

-- Linear Hierarchy (all one to many each node):
--     system -> subject -> topic -> doc
-- ie.   wissen -> overview -> hierarchy -> wissen intends to make it easier to find docs.

BEGIN;

CREATE TABLE system (system_name PRIMARY KEY, label);

CREATE TABLE subject (subject_name PRIMARY KEY, label,
  system_name REFERENCES system (system_name));

CREATE TABLE topic (topic_name PRIMARY KEY, label,
  subject_name REFERENCES subject (subject_name));

CREATE TABLE doc (doc_name PRIMARY KEY, label, doc,
  topic_name REFERENCES topic (topic_name));

INSERT INTO system (system_name, label)
VALUES
  ('wissen', 'Wissen')
;

INSERT INTO subject (system_name, subject_name, label)
VALUES
  ('wissen', 'overview', 'Overview')
;

INSERT INTO topic (subject_name, topic_name, label)
VALUES
  ('overview', 'general', 'General')
  ('overview', 'hierarchy', 'Data Hierarchy')
;

INSERT INTO doc (topic_name, doc_name, label, doc)
VALUES
  ('general', 'abstract', 'Abstract', 'The intent is to index a lot of docs.')
, ('general', 'why', 'Why do this?', 'I feel that having so many doc systems is superflous, to the point that almost no developers spend adequate time on reading documentation, and instead opt for short blog posts etc.')

, ('hierarchy', 'abstract', 'Abstract', 'Data is nested as such:  system -> subject -> topic -> doc ')
;

-- Pretty print
.headers on
.mode column

SELECT ROWID, * FROM doc;

SELECT sy.label, s.label, t.label
, d.label, d.doc
FROM doc d
JOIN topic t USING (topic_name)
JOIN subject s USING (subject_name)
JOIN system sy USING (system_name)
;

COMMIT;
