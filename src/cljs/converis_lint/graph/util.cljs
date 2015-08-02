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

(defn is-included [graph data-entity]
  (not (empty? (some #{data-entity} 
              (:included-entities graph)))))

(defn include-all [db]
  (assoc (:data-model-graph db) :included-entities 
         (mapv key (:dataentitytypes (:data-model db)))))

(defn update-graph [db graph]
  (let [node-lookup (node-lookup-map graph)]
    (assoc graph 
           :nodes
           (mapv #(if (contains? node-lookup (key %1))
                    (get node-lookup (key %1))                    
                    (merge {:id (key %1)
                            :x (rand 2)
                            :y (rand 2)})) 
                 (filter #(is-included graph (key %1)) (:dataentitytypes (:data-model db))))
           :edges  
           (mapv #(merge {:n1 (:left %1)
                          :n2 (:right %1)})
                 (filter #(and (not (= (:left %1) (:right %1)))
                               (is-included graph (:left %1))
                               (is-included graph (:right %1)))
                         (mutils/get-link-entity-types (:data-model db))))
           )))

