(ns converis-lint.views.templateutil
    (:require [clojure.string :as str]
              [converis-lint.modelutils :as mutils]
              [reagent.core :as reagent]
              [clojure.zip :as zip]
              [re-com.buttons :as buttons]
              [re-com.core :as re-com]
              [re-frame.core :as re-frame])

)

(defn name-and-id [name elem]
  (list [:span {:class "element-type right-margin"} name
        (if-not (nil? (get-in elem [:attrs :id]))
          [:span {:class "element-id left-margin"} (get-in elem [:attrs :id])])])
)

(defn attribute-span 
  ([key element]
   (attribute-span (str/capitalize (name key)) key element))
  ([label key element]
  (if-not (nil? (get-in element [:attrs key]))
    [:span {:class "attribute-span"}
     (str label ": " (get-in element [:attrs key]))])
  )
)

(defn- other-side [det link-name datamodel]
  (if (string? link-name)
    (if (> (.indexOf link-name ",") -1)
      (recur det (str/split link-name ",") datamodel) 
      (let [link-type (mutils/get-link-entity-type datamodel link-name true)]
        (if (= (:left link-type) det)
          (:right link-type)
          (:left link-type))))
    (let [new-start (other-side det (first link-name) datamodel)]
      (if (> (count link-name) 1)
        (recur new-start (rest link-name) datamodel)
        new-start
        ))))

(defn last-link [link-name]
    (last (str/split link-name ",")))

(defn text-element [text]
  [re-com/h-box
   :children [(name-and-id "Text" text)
              [:span (get-in text [:attrs :value])]
              (if-not (nil? (get-in text [:attrs :textStyle]))
                [:span {:class "left-margin"} (str "Style: " 
                                                   (get-in text [:attrs :textStyle]))])
              ]])

(defn unused-attrs [element used]
  (let [unused (apply dissoc (:attrs element)
                       used)]
    (if-not (empty? unused)
                  [:span (str unused)])))

(defn first-section [template]
  (get-in (first (:content (:template template))) [:attrs :name])
)

(defn is-edit-template[type]
  (not (nil? (some #{type} '("EDIT_VIEW" "EDIT_VIEW_CHILD")))))

