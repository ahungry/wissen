;; https://github.com/ogrim/clojure-sqlite-example

(ns wissen.core
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [clojure.spec.test.alpha :as stest]
   [clojure.java.jdbc :refer :all]
   )
  (:gen-class))

(def db
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname "wissen.db"})

(defn systems []
  (query db (slurp "sql/q-systems.sql")))

(defn blub [{:keys [a b c] :or {a 1} :as m}]
  (println a)
  (println b)
  (println c)
  (println m))

(defn subjects [{:keys [system]} ]
  (query db [(slurp "sql/q-subjects.sql") system]))

(defn topics [{:keys [system subject]} ]
  (query db [(slurp "sql/q-topics.sql") system subject]))

(defn docs [{:keys [system subject topic]}]
  (query db [(slurp "sql/q-docs.sql") system subject topic]))

(defn next-level
  "Accessible with something like (next-level [subjects topics docs] (systems))."
  [[f & rest] col]
  (if rest
    (doall (map (fn [parent] (next-level rest (apply f [parent]))) col))
    (doall (map (fn [parent] (apply f [parent])) col))
    ))

(defn info-out! [{:keys [doc label] :as m}]
  (println (format "Label: %s\n%s" label doc ))
  m)

(defn hierarchy []
  (next-level
   [subjects
    info-out!
    topics
    info-out!
    docs
    info-out!
    ] (systems)))

(defn test [db]
  (query db "select count(*) FROM doc"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
