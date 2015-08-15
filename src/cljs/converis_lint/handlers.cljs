(ns converis-lint.handlers
    (:require [re-frame.core :as re-frame]
              [converis-lint.graph.util :as graphutil]
              [converis-lint.graph.frlayout :as graph]
              [converis-lint.modelutils :as mutils]
              [converis-lint.assessment :as asmt]
              [converis-lint.views.templateutil :as tutil]
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

(defn load-choice-groups-handler[cgs]
  (re-frame/dispatch [:choice-groups cgs])
)

(defn- fetch-choice-groups []
  (GET "/choicegroups" {:handler load-choice-groups-handler 
                        :response-format :json
                        :keywords? true}))

(defn load-data-model-handler[model]
  (re-frame/dispatch [:data-model model])
)

(defn- fetch-data-model []
  (GET "/datamodel" {:handler load-data-model-handler 
                        :response-format :json
                        :keywords? true}))


(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   (fetch-choice-groups)
   (fetch-data-model)
   db/default-db
))

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
 :choice-groups
 (fn [db [_ cgs]]
   (assoc db :choice-groups cgs)))


(re-frame/register-handler
 :data-model
 (fn [db [_ model]]
   (let [db-with-model (assoc db :data-model model)]
     (fetch-templates (name (first (sort (keys (:dataentitytypes model))))))
     (assoc db-with-model 
            :data-model-graph (init-graph db-with-model)
            :current-data-entity (first (sort (keys (:dataentitytypes model))))))
))

(re-frame/register-handler
 :templates
 (fn [db [_ templates]]
   (let [template (first templates)
         current-section (if (tutil/is-edit-template (:templateType template))
                           (tutil/first-section template)
                           nil)]
     (assoc db :templates templates
            :current-template template
            :current-section current-section))
   ))

(re-frame/register-handler
 :current-template
 (fn [db [_ template]]
   (let [current-section (if (tutil/is-edit-template (:templateType template))
                           (tutil/first-section template)
                           nil)]
     (assoc db :current-template template
            :current-section current-section))
   ))

(re-frame/register-handler
 :current-section
 (fn [db [_ section]]
   (assoc db :current-section section)
   ))


(re-frame/register-handler
 :current-data-entity
 (fn [db [_ entity]]
   (fetch-templates (name entity))
   (assoc db :current-data-entity entity
          :current-template nil
          :current-section nil)
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
