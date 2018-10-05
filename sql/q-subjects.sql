SELECT subject_name AS subject
  , doc
  , label
  , system_name AS system
FROM subject
WHERE system_name = $1
;
