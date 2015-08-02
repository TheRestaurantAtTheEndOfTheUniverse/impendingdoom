(ns converis-lint.views.template
    (:require [clojure.string :as str]
              [converis-lint.modelutils :as mutils]
              [reagent.core :as reagent]
              [re-com.buttons :as buttons]
              [re-com.core :as re-com]
              [re-frame.core :as re-frame])

)


(declare display-template)

(defn name-and-id [name elem]
  (list [:span {:class "element-type"} name]
        (if-not (nil? (get-in elem [:attrs :id]))
          [:span {:class "element-id"} (get-in elem [:attrs :id])]))
)

(defn attribute-span [label key element]
  (if-not (nil? (get-in element [:attrs key]))
    [:span {:class "attribute-span"}
     (str label ": " (get-in element [:attrs key]))])
)

(defn display-parts [elem datamodel current-det]
  (for [part (:content elem)]
    (display-template part datamodel current-det)))


(defn simple-element [elem]
  [re-com/h-box
   :children [(name-and-id (str/capitalize (:tag elem)) elem)
              ]])


(defn- text-element [text]
  [re-com/h-box
   :children [(name-and-id "Text" text)
              [:span (get-in text [:attrs :value])]
              (if-not (nil? (get-in text [:attrs :textStyle]))
                [:span (str "Style" (get-in text [:attrs :textStyle]))])
              ]])

(defn- attribute-element[attribute det]
  [re-com/h-box
   :children [[:div {:class "element-type"} "Attribute"]
              [:div {:class "element-id"} (get-in attribute [:attrs :id])]
              [:div (str (get-in attribute [:attrs :name]) " of " det)]
              ]])

(defn- listdisplay-element[attribute]
  [re-com/h-box
   :children [(name-and-id "List display" attribute)
              [:div (str "Lazy count "(get-in attribute [:attrs :lazyCount]) " Lazy load " (get-in attribute [:attrs :lazyLoad]))]
              ]])

(defn- iolink-element []
  [re-com/h-box
   :children [[:div {:class "element-type"} "IO Link"]
              ]])

(defn- block-element []
  [re-com/h-box
   :children [[:div {:class "element-type"} "Block"]
              ]])

(defn- render-element []
  [re-com/h-box
   :children [[:div {:class "element-type"} "Render"]
              ]])

(defn- style-element [style]
  [re-com/v-box
   :children [[re-com/h-box
               :children [(name-and-id "Style" style)
                          (attribute-span "Color" :color style)
                          (attribute-span "Font weight" :font-weight style)
                          (attribute-span "Font size" :font-size style)
                          (attribute-span "Line height" :line-height style)]]
              [:div {:class "template-examplebox" :style (select-keys (:attrs style) [:color :font-weight :font-size :line-height])} "The quick brown fox jumps over the lazy dog"]
]])

(defn- linebreak-element []
  [re-com/h-box
   :children [[:div {:class "element-type"} "Line break"]
              ]])


(defn- eval-element [eval]
  [re-com/h-box
   :children [[:div {:class "element-type"} "Eval"]
              (condp = (get-in eval [:attrs :operator])
                "set" (list [:span {:style {:padding-right "5px"}} "When"]
                            [:span {:class "element-id"} (get-in eval [:attrs :elementId])]
                            [:span " is not empty"])
                "notset" (list [:span {:style {:padding-right "5px"}} "When"]
                            [:span {:class "element-id"} (get-in eval [:attrs :elementId])]
                            [:span " is empty"])
                (list [:span (get-in eval [:attrs :operator])]
                      [:span {:class "element-id"} (get-in eval [:attrs :elementId])]))]]
                )

(defn- paginator-element [eval]
  [re-com/h-box
   :children [[:div {:class "element-type"} "Paginator"]
              [:span {:style {:padding-right "5px"}} "for"]
              [:span {:class "element-id"} (get-in eval [:attrs :for])]]]
                )

(defn- label-element [label]
  [re-com/h-box
   :children [[:div {:class "element-type"} "Label"]
              [:div {:class "element-id"} (get-in label [:attrs :id])]
              (str (get-in label [:attrs :bundleName]) "." 
                   (get-in label [:attrs :labelKey]))]
   ])


(defn- other-side [det link datamodel]
  (let [link-name (get-in link [:attrs :name])
        link-type (mutils/get-link-entity-type datamodel link-name   true)]
    (if (= (:left link-type) det)
                     (:right link-type)
                     (:left link-type))))

(defn- link-element[link datamodel det]
  (let [link-name (get-in link [:attrs :name])
        other-side (other-side det link datamodel)
        ]
    [re-com/h-box
     :children [[:div {:class "element-type"} "Link"]
                [:div {:class "element-id"} (get-in link [:attrs :id])]
                [:div 
                 [:span {:style {:padding-right "5px"}} "Going from"]  
                 [:span {:class "template-det"} det] 
                 [:span " to "] 
                 [:span {:class "template-det"} other-side] 
                 [:span " via "] 
                 [:span {:class "template-link"} link-name]]
                ]]))


(defn display-template[template datamodel current-det]
  (re-com/v-box 
   :class "element"
   :children [
              (condp = (:tag template)
                "converisoutput" [:div 
                                  [:div (str "Output template " current-det)]
                                  (display-parts template datamodel current-det)]
                "text" [:div (text-element template)
                        (display-parts template datamodel current-det)]
                "iot_attribute" [:div (attribute-element template current-det)
                        (display-parts template datamodel current-det)]
                "iolink" [:div (iolink-element)
                        (display-parts template datamodel current-det)]
                "block" [:div (block-element)
                        (display-parts template datamodel current-det)]
                "relation" [:div (link-element template datamodel current-det)
                        (display-parts template datamodel 
                                       (other-side current-det template datamodel))]
                "render" [:div (render-element)
                        (display-parts template datamodel current-det)]
                "eval" [:div (eval-element template)
                        (display-parts template datamodel current-det)]
                "table" [:div
                         [:div (name-and-id "Table" template)]
                         [:table {:class "template-table"} 
                         [:tbody
                          [:tr (doall (map #(vector :td 
                                                     (display-template %1 datamodel current-det))
                                            (:content template)))]]]]
                "style" [:div (style-element template)
                         (display-parts template datamodel current-det)]
                "column" (display-parts template datamodel current-det)
                "label" [:div (label-element template)
                         (display-parts template datamodel current-det)]
                "linebreak" [:div (linebreak-element)
                         (display-parts template datamodel current-det)]
                "listdisplay" [:div (listdisplay-element template)
                         (display-parts template datamodel current-det)]
                "or" [:div (simple-element template)
                         (display-parts template datamodel current-det)]
                "and" [:div (simple-element template)
                         (display-parts template datamodel current-det)]
                "paginator" [:div (paginator-element template)
                         (display-parts template datamodel current-det)]
                [:div (str (:tag template) template)
                 (display-parts template datamodel current-det)]
                )
              ]))

