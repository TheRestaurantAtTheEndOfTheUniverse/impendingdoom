(ns converis-lint.assessment
    (:require [converis-lint.config :as config]
              [converis-lint.modelutils :as mutils])
)

(def max-attribute-count 50)
(def attribute-cost 
  {"BOOLEAN" 1
   "CGV" 9
   "DATE" 8
   "NUMBER" 32
   "STRING" 100
   "TEXT" 1000
   }
)


(defn- assess-attributes-count[model-info]
  (let [attribute-count (count (:attributeDefinitions model-info))]
    (if (> attribute-count max-attribute-count)
      (list {:type :attribute-count
       :weight (* 0.25 (- attribute-count max-attribute-count))})))
)

(defn- assess-description[model-info]
  (list {:type :missing-description
         :weight 0.2})
)

(defn- assess-binary-attributes[data-entity-type]
  (let [binary (filter #(= (:dataType %1) "BINARY") 
                       (:attributeDefinitions data-entity-type))]
    (map #(merge {:type :binary-attribute
                  :weight 1
                  :location {:name %1}}) binary)
    ))

(defn- assess-text-attributes[model-info]
  (reduce #(if (and (not (contains? mutils/internal-attributes (:name %2)))
                    (= (:dataType %2) "TEXT"))
             (conj %1 {:type :text-attribute
                    :weight 2
                       :location (:name %2)})
             %1)
          ()
          (:attributeDefinitions model-info))
)

(defn assess [entity]
  (concat  (assess-attributes-count entity)
           (assess-binary-attributes entity)
           (assess-text-attributes entity)
           (assess-description entity))
)

(defn data-entity-weight[entity]
  (reduce #(+ %1 (get attribute-cost (:dataType %2)))
          0
          (:attributeDefinitions entity))
)


(defn centrality [data-model data-entity-type]
  (let [links (mutils/get-link-entity-types data-model)]
    (/ (count (filter #(and (not (= (:left %1) (:right %1)))
                    (or (= (:left %1) data-entity-type)
                        (= (:right %1) data-entity-type)))
             links))
       (count (filter #(not (= (:left %1) (:right %1))) 
                      links)))))


(defn assess-edit-template[current-det template]

)
