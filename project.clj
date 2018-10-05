(defproject wissen "0.1.0-SNAPSHOT"
  :description "Wissen - Documentation Stuff"
  :url "https://github.com/ahungry/wissen"
  :license {:name "GPLv3 or Later"
            :url "http://www.gnu.org/licenses/"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot wissen.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
