(ns converis-lint.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

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
   (reaction (:included-entities (:data-model-graph @db)))
))

(re-frame/register-sub
 :data-model
 (fn [db]
   (reaction (:data-model @db))
))

(re-frame/register-sub
 :choice-groups
 (fn [db]
   (reaction (:choice-groups @db))
))

(re-frame/register-sub
 :templates
 (fn [db]
   (reaction (:templates @db))
))

(re-frame/register-sub
 :current-templates
 (fn [db]
   (reaction (get (:templates @db) (:current-data-entity @db)))
))


(re-frame/register-sub
 :focused-data-entity
 (fn [db]
   (reaction -1)
))
