(ns converis-lint.server
  (:require [clojure.data.json :as json]
            [clojure.data.zip.xml :as zx]
            [clojure.string :as str]
            [clojure.data.xml :as xml]
            [clojure.zip :as zip]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [converis-lint.templates :as templates]
            [clojure.stacktrace]
            [ring.util.response :refer :all])
  (:import [java.io StringReader])
)


(defn group-by*
  "Similar to group-by, but takes a collection of functions and returns
  a hierarchically grouped result."
  [fs coll]
  (if-let [f (first fs)]
    (into {} (map (fn [[k vs]]
                    [k (group-by* (next fs) vs)])
               (group-by f coll)))
    coll))

(defn convert-template [template]
  (assoc template :template
         (try 
           (doall (xml/parse-str (:template template)))
           (catch Throwable e
             (clojure.stacktrace/print-stack-trace e)
             )))
  )

(def processed-templates
  (group-by* [:dataEntityType :templateType] templates/templates)
)

(defroutes server
  (GET "/templates/:det/:templatetype" [det templatetype] 
       (-> (json/write-str (convert-template (first (get-in processed-templates [det templatetype]))))
           (response)
           (content-type "application/json"))
       )

  (GET "/templates/:det" [det] 
       (do
         (println (flatten (map val (get processed-templates det))))
         
       (-> (json/write-str (mapv convert-template (flatten (map val (get processed-templates det)))))
           (response)
           (content-type "application/json"))
       ))



  (GET "/" [] 
                (-> ;(json/write-str (convert-template (second templates/templates)))
                 (json/write-str processed-templates)
                 (response)
                 (content-type "text/plain"))
)

)
