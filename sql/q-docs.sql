SELECT doc_name
  , doc
  , label
FROM doc
WHERE system_name = $1
  AND subject_name = $2
  AND topic_name = $3
;
