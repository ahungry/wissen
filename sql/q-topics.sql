SELECT topic_name AS topic
  , doc
  , label
FROM topic
WHERE system_name = $1
  AND subject_name = $2
;
