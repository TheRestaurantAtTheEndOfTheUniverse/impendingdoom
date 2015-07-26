(ns converis-lint.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]
              [converis-lint.graph :as graph]))

(re-frame/register-sub
 :name
 (fn [db]
   (reaction (:name @db))))

(re-frame/register-sub
 :stage
 (fn [db]
   (reaction (:stage @db))
))

(re-frame/register-sub
 :current-data-entity
 (fn [db]
   (reaction (:current-data-entity @db))
))

(re-frame/register-sub
 :data-model-graph
 (fn [db]
   (reaction (:data-model-graph @db))
))

(re-frame/register-sub
 :data-model-graph-visible-entities
 (fn [db]
   (reaction (:data-model-graph-visible-entities @db))
   (reaction (:data-model-graph @db))
))
