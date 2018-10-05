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

(defn chapter-or-section [{:keys [type]}]
  (cond (= type "subject") "chapter"
        :else "section"))

(def buf (atom ""))

;; May as well be a mutable since this is highly suspect to race conditions, oh well.
(defn push-to-buf! [s]
  (swap! buf #(apply str [% s])))

(defn printb [s]
  (println s)
  (push-to-buf! s))

(defn menu-item-out! [{:keys [id label] :as m}]
  (if label (printb (format "* %s(%s)::\n" label id)))
  ;; We wrap in a list so we can chain in next-level
  [m])

(defn menu-out! [{:keys [doc label] :as m}]
  (printb "\n@menu\n")
  (doall (map menu-item-out! (subjects m)))
  (printb "@end menu\n\n")
  ;; We wrap in a list so we can chain in next-level
  [m])

(defn info-out! [{:keys [doc label id] :as m}]
  (if label (printb (format "\n@node %s(%s)\n@%s %s(%s)\n" label id (chapter-or-section m) label id)))
  (if doc (printb (format "%s\n" doc)))
  ;; We wrap in a list so we can chain in next-level
  [m])

(defn next-level
  "Accessible with something like (next-level [subjects topics docs] (systems))."
  [[f & rest] col]
  (doall
   (if rest
     (map (fn [parent] (next-level rest (apply f [parent]))) col)
     (map (fn [parent] (apply f [parent])) col)
     )))

(defn hierarchy []
  (next-level
   [info-out!
    topics
    info-out!
    menu-out!
    docs
    info-out!
    ;; menu-out!
    ;; menu-out!
    ;; topics
    ;; menu-start!
    ;; menu-out!
    ;; menu-stop!
    ;; info-out!
    ;; docs
    ;; menu-start!
    ;; menu-out!
    ;; menu-stop!
    ;; info-out!
    ] (subjects {:system "wissen"}))
  nil)

(defn top-level-menu [col]
  (next-level
   [menu-item-out!] col))

(defn output-texinfo! []
  (reset! buf "")
  (printb "\\input texinfo @c -*- texinfo -*
@c %**start of header
@setfilename wissen.info
@settitle Wissen
@documentencoding UTF-8
@documentlanguage en
@c %**end of header

@finalout
@titlepage
@title Wissen
@author Matthew Carter
@end titlepage

@contents

@ifnottex
@node Top
@top Wissen

Wissen

@end ifnottex

@menu
")
  (top-level-menu (subjects {:system "wissen"}))
  ;; (printb "
;; @detailmenu
;; --- The Detailed Node Listing ---

;; Wissen

;; * Second level menu::

;; @end detailmenu
;; @end menu

;; ")
  (printb "@end menu\n\n")
  (hierarchy)
  (printb "\n@bye\n")
  (spit "/tmp/wissen.texi" @buf))

(defn test [db]
  (query db "select count(*) FROM doc"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
