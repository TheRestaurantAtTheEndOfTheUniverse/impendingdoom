(ns converis-lint.server
  (:require 
   [converis-lint.templates :as templates]
   [compojure.core :refer :all]
   [compojure.handler :as handler]
   [ring.util.response :refer :all]
   [clojure.zip :as zip]
   [clojure.data.zip.xml :as zx]
   [clojure.data.xml :as xml]
   [clojure.data.json :as json]
   ))



(def processed-templates
  (mapv #(:template %1) templates/templates)
)


(defroutes server
  (GET "/templates/:det/:templatetype" [det templatetype] (str "Template " det templatetype))
  (GET "/" [] 
                (-> (json/write-str processed-templates)
                    (response)
                    (content-type "text/json"))
)

)
