SELECT subject_name AS subject
  , doc
  , label
  , system_name AS system
  , 'subject' AS type
  , ROWID as id
FROM subject
WHERE system_name = $1
;
