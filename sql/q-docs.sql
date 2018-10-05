SELECT doc_name
  , doc
  , label
  , system_name AS system
  , subject_name AS subject
  , topic_name AS topic
FROM doc
WHERE system_name = $1
  AND subject_name = $2
  AND topic_name = $3
;
