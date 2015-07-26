(ns converis-lint.model-utils
    (:require [converis-lint.db :as db])
)

(def internal-attributes ["FShort description"
                          "Created by"
                          "Created on"
                          "Updated by"
                          "Updated on"
                          "Internal visible"
                          "Public visible"
                          "Short description"
                          "Status"
                          "intern manual"
                          "public manual"])

(defn is-internal-attribute[attr]
  ;; Check if the attribute is an internal one
  (not (nil? (some #{attr} internal-attributes)))
)

(defn data-entity [datamodel id] 
  (get (:dataentitytypes datamodel) id))

(defn data-entity-name [id]
  id
)

(defn data-entity-list [datamodel]
  (reduce #(conj %1 (assoc {} :id (key %2) :label (key %2)))
          []
          (:dataentitytypes datamodel)))

(defn connected-data-entities [db entity]
  (reduce #(if (= entity (:left %2))
             (conj %1 (:right %2))
             (if (= entity (:right %2))
               (conj %1 (:left %2))
               %1))
          #{}
          (:linkentitytypes (:data-model db))))
