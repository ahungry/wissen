SELECT subject_name AS subject
  , doc
  , label
FROM subject
WHERE system_name = $1
;
