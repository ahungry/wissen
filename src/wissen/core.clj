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
  (if label (printb (format "* %s. %s::\n" id label)))
  ;; We wrap in a list so we can chain in next-level
  [m])

(defn menu-out! [{:keys [doc label] :as m}]
  (printb "\n@menu\n")
  (doall (map menu-item-out! (subjects m)))
  (printb "@end menu\n\n")
  ;; We wrap in a list so we can chain in next-level
  [m])

(defn clean-doc
  "makeinfo is not a fan of unescaped braces, escape with leading @."
  [s]
  (clojure.string/replace s #"[{}]" #(str "@" %1)))

(defn info-out! [{:keys [doc label id] :as m}]
  (if label (printb (format "\n@node %s. %s\n@%s %s. %s\n" id label (chapter-or-section m) id label)))
  (if doc (printb (format "%s\n" (clean-doc doc))))
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

(defn hierarchy [sys]
  (next-level
   [info-out!
    topics info-out! menu-out!
    docs info-out!
    ] (subjects {:system sys}))
  nil)

(defn top-level-menu [col]
  (next-level
   [menu-item-out!] col))

(defn output-texinfo! [sys sys-label]
  (reset! buf "")
  (printb (format "\\input texinfo @c -*- texinfo -*
@c %%**start of header
@setfilename %s.info
@settitle %s
@documentencoding UTF-8
@documentlanguage en
@c %%**end of header

@finalout
@titlepage
@title %s
@author Generated by Wissen
@end titlepage

@contents

@ifnottex
@node Top
@top %s

%s

@end ifnottex

@menu
" sys sys-label sys-label sys-label sys-label))
  (top-level-menu (subjects {:system sys}))
  ;; (printb "
;; @detailmenu
;; --- The Detailed Node Listing ---

;; Wissen

;; * Second level menu::

;; @end detailmenu
;; @end menu

;; ")
  (printb "@end menu\n\n")
  (hierarchy sys)
  (printb "\n@bye\n")
  (spit (format "/tmp/%s.texi" sys) @buf))

(defn sqltest [db]
  (query db "select count(*) FROM doc"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (apply output-texinfo! args))
