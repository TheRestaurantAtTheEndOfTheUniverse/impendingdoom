(ns converis-lint.handlers
    (:require [re-frame.core :as re-frame]
              [converis-lint.graph.util :as graphutil]
              [converis-lint.graph :as graph]
              [converis-lint.db :as db]))


(defn init-graph [db]  
  (graphutil/update-graph db 
                          (merge {:temp 1
                                  :iteration 50
                                  :draw-edges true
                                  :included-entities (mapv key (:dataentitytypes (:data-model db)))
                                  :all-entities (mapv key (:dataentitytypes (:data-model db)))}
                                 
)))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   ;;(.log js/console (str "Initial Graph: " (init-graph db/default-db)))
   (assoc db/default-db :data-model-graph (init-graph db/default-db))))

(re-frame/register-handler
 :name-change
 (fn [db [_ name]]
   (assoc db :name name)))

(re-frame/register-handler
 :stage
 (fn [db [_ stage]]
   (assoc db :stage stage)))

(re-frame/register-handler
 :current-data-entity
 (fn [db [_ entity]]
   (assoc db :current-data-entity entity)))

(re-frame/register-handler
 :data-model-graph
 (fn [db [_ graph]]
   (assoc db :data-model-graph graph)))

(re-frame/register-handler
 :data-model-graph-visible-entities
 (fn [db [_ entities]]
   (let [graph (:data-model-graph db)
         iteration-running (> (:iteration graph) 0)
         graph-with-included (assoc graph :included-entities entities
                                    :iteration 20
                                    :temp 1)
         updated-graph (graphutil/update-graph db graph-with-included)]
     (.log js/console (str "Count of sent entities: " (count entities)))
     (.log js/console (str "Count of updated entities: " (count (:included-entities graph))))
     (if-not iteration-running
       (js/setTimeout graph/updater 20))
     (assoc db :data-model-graph updated-graph)
     )))


