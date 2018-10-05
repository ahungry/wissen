SELECT topic_name AS topic
  , doc
  , label
  , system_name AS system
  , subject_name AS subject
  , 'topic' AS type
FROM topic
WHERE system_name = $1
  AND subject_name = $2
;
