(ns converis-lint.graph.util
    (:require 
              [converis-lint.db :as db]
              [converis-lint.modelutils :as mutils]
              ))

(defn node-lookup-map[graph]
  (reduce #(assoc %1 (:id %2) %2)
          {}
          (:nodes graph))
)


(defn log [msg]
  (.log js/console msg)
)

(defn is-included [entities data-entity]
  (not (nil? (some #{data-entity} 
              entities))))

(defn include-all [db]
  (assoc (:data-model-graph db) :included-entities 
         (mapv key (:dataentitytypes (:data-model db)))))

(defn update-graph [db graph]
  (let [node-lookup (node-lookup-map graph)
        included-entities (:included-entities graph)
        included-names (mapv name included-entities)]
    (log (str node-lookup))
    (assoc graph 
           :nodes
           (mapv #(if (contains? node-lookup (name (key %1)))
                    (get node-lookup (name (key %1)))                    
                    (merge {:id (name (key %1))
                            :x (rand 2)
                            :y (rand 2)})) 
                 (filter #(is-included included-entities (key %1)) (:dataentitytypes (:data-model db))))
           :edges  
           (mapv #(merge {:n1 (:left %1)
                          :n2 (:right %1)})
                 (filter #(and (not (= (:left %1) (:right %1)))
                               (is-included included-names (:left %1))
                               (is-included included-names (:right %1)))
                         (mutils/get-link-entity-types (:data-model db))))
           )))

