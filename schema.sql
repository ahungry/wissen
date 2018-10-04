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

-- Is this a mess, or is it OK?  Its basically going to chain with
-- each node requiring an extra column.

CREATE TABLE system (
    system_name PRIMARY KEY
  , label
  , doc
);

CREATE TABLE subject (
    system_name REFERENCES system (system_name) ON UPDATE CASCADE
  , subject_name
  , label
  , doc
  , PRIMARY KEY (subject_name, system_name)
);

CREATE TABLE topic (
    system_name REFERENCES system (system_name) ON UPDATE CASCADE
  , subject_name REFERENCES subject (subject_name) ON UPDATE CASCADE
  , topic_name
  , label
  , doc
  , PRIMARY KEY (topic_name, subject_name, system_name)
);

CREATE TABLE doc (
    system_name REFERENCES system (system_name) ON UPDATE CASCADE
  , subject_name REFERENCES subject (subject_name) ON UPDATE CASCADE
  , topic_name REFERENCES topic (topic_name) ON UPDATE CASCADE
  , doc_name
  , label
  , doc
  , PRIMARY KEY (doc_name, topic_name, subject_name, system_name)
);

INSERT INTO system (system_name, label)
VALUES
  ('wissen', 'Wissen (Documentation system)')
, ('ahubu', 'AHUBU (Ahungry web browser)')
;

INSERT INTO subject (system_name, subject_name, label)
VALUES
  ('wissen', 'overview', 'Overview')
, ('wissen', 'support', 'Support')
, ('ahubu', 'overview', 'Overview')
, ('ahubu', 'support', 'Support')
;

INSERT INTO topic (system_name, subject_name, topic_name, label)
VALUES
  ('wissen', 'overview', 'general', 'General')
, ('wissen', 'overview', 'hierarchy', 'Data Hierarchy')
, ('ahubu', 'overview', 'general', 'General')
;

INSERT INTO doc (system_name, subject_name, topic_name, doc_name, label, doc)
VALUES
  ('wissen', 'overview', 'general', 'abstract', 'Abstract', 'The intent is to index a lot of docs.')
, ('wissen', 'overview', 'general', 'why', 'Why do this?', 'I feel that having so many doc systems is superflous, to the point that almost no developers spend adequate time on reading documentation, and instead opt for short blog posts etc.')
, ('wissen', 'overview', 'general', 'refs', 'References', 'https://github.com/ahungry/wissen')

, ('wissen', 'overview', 'hierarchy', 'abstract', 'Abstract', 'Data is nested as such:  system -> subject -> topic -> doc ')
, ('ahubu', 'overview', 'general', 'abstract', 'Abstract', 'Just a highly specialized browser.')
, ('ahubu', 'overview', 'general', 'refs', 'References', 'https://github.com/ahungry/ahubu')
;

-- Pretty print
.headers on
.mode column

-- SELECT ROWID, * FROM doc;
select * from topic;

SELECT COALESCE(sy.label, sy.system_name) AS system
  , COALESCE(s.label, s.subject_name) AS subject
  , COALESCE(t.label, t.topic_name) AS topic
  , COALESCE(d.label, d.doc_name) AS doc
  , d.doc
FROM doc d
JOIN topic t USING (topic_name, subject_name, system_name)
JOIN subject s USING (subject_name, system_name)
JOIN system sy USING (system_name)
;

COMMIT;
