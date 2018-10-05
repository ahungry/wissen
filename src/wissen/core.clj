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

(defn subjects [system]
  (query db [(slurp "sql/q-subjects.sql") system]))

(defn topics [system subject]
  (query db [(slurp "sql/q-topics.sql") system subject]))

(defn docs [system subject topic]
  (query db [(slurp "sql/q-docs.sql") system subject topic]))

(defn hierarchy []
  (->> (systems)
       (map #(subjects (:system %)))
       (map #(topics (:system %) (:subject %)))
       (map #(docs (:system %) (:subject %) (:topic %)))
       count
       ))

(defn test [db]
  (query db "select count(*) FROM doc"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
