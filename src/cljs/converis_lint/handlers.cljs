(ns converis-lint.handlers
    (:require [re-frame.core :as re-frame]
              [converis-lint.graph.util :as graphutil]
              [converis-lint.graph.frlayout :as graph]
              [converis-lint.modelutils :as mutils]
              [converis-lint.assessment :as asmt]
              [converis-lint.db :as db]
              [ajax.core :refer [GET POST]]))


(defn init-graph [db]  
  (graph/fr-layout (graphutil/update-graph db 
                          (merge {
                                  :iteration 0
                                  :total-frames 50
                                  :draw-edges true
                                  :included-entities (mapv key (:dataentitytypes (:data-model db)))
                                  :all-entities (mapv key (:dataentitytypes (:data-model db)))}
                                 )) 1 50))

(defn change-visible-entities [db entities]
  ;; Change items visible in the explorer
  (let [graph (:data-model-graph db)
        ;; Animation already running?
        iteration-running (> (:iteration graph) 0)
        ;; Update included entities
        graph-with-included (assoc graph :included-entities entities
                                    :iteration 30
                                    :total-frames 30)
        ;; Update nodes and edges
        updated-graph (graphutil/update-graph db graph-with-included)]
    (if-not iteration-running
      (js/setTimeout graph/updater 20))
    ;; Recalculate layout
    (assoc db :data-model-graph (graph/fr-layout updated-graph 1 50))
    ))

(defn load-templates-handler[templates]
  (re-frame/dispatch [:templates templates])
)

(defn- fetch-templates [entity]
  (GET (str "/templates/" entity) {:handler load-templates-handler 
                                   :response-format :json
                                   :keywords? true}))


(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   (.log js/console (str "Entity: " (:current-data-entity db/default-db)))
   (fetch-templates (:current-data-entity db/default-db))
   (assoc db/default-db :data-model-graph (init-graph db/default-db))))

(re-frame/register-handler
 :name-change
 (fn [db [_ name]]
   (assoc db :name name)))

(re-frame/register-handler
 :stage
 (fn [db [_ stage]]
   (let [new-screen (if (= (:stage db) stage)
                      (:screen db)
                      nil)]
   (assoc db :stage stage
          :screen new-screen))))

(re-frame/register-handler
 :screen
 (fn [db [_ screen]]
   (assoc db :screen screen)))

(re-frame/register-handler
 :templates
 (fn [db [_ templates]]
   (assoc db :templates templates)
   ))

(re-frame/register-handler
 :current-template
 (fn [db [_ template]]
   (assoc db :current-template template)
   ))

(re-frame/register-handler
 :current-data-entity
 (fn [db [_ entity]]
   (fetch-templates entity)
   (assoc db :current-data-entity entity)
   ))

(re-frame/register-handler
 :data-model-graph
 (fn [db [_ graph]]
   (assoc db :data-model-graph graph)))

(re-frame/register-handler
 :data-model-graph-visible-entities
 (fn [db [_ entities]]
   (change-visible-entities db entities)))

(re-frame/register-handler
 ;; Focus on a specific entities
 ;; This will make the focused entity
 ;; and all directly connected entities visible
 :focused-data-entity
 (fn [db [_ entity]]
   (let [visible-entities (conj (mutils/connected-data-entities db entity) entity)]
         (change-visible-entities db (vec visible-entities)))))
