(ns converis-lint.views.outputtemplate
    (:require [clojure.string :as str]
              [converis-lint.modelutils :as mutils]
              [converis-lint.views.templateutil :as tutil]
              [reagent.core :as reagent]
              [clojure.zip :as zip]
              [re-com.buttons :as buttons]
              [re-com.core :as re-com]
              [re-frame.core :as re-frame])

)


(declare display-template)



(defn- display-parts [elem datamodel current-det & {:keys [no-indent] :or {no-indent false}}]
  (for [part (:content elem)]
    (display-template part datamodel current-det :no-indent no-indent)))


(defn simple-element [elem]
  [re-com/h-box
   :children [(tutil/name-and-id (str/capitalize (:tag elem)) elem)
              ]])

(defn- separated-display-element [disp]
  [re-com/h-box
   :children [(tutil/name-and-id "Separated display" disp)
              [:span (get-in disp [:attrs :value])]
              (if-not (nil? (get-in disp [:attrs :separator]))
                [:span (get-in disp [:attrs :separator])])
              ]])

(defn- image-element [disp]
  [re-com/h-box
   :children [(tutil/name-and-id "Image" disp)
              [:span (get-in disp [:attrs :imgSrc])]]])

(defn- data-entity-type-element [disp]
  [re-com/h-box
   :children [(tutil/name-and-id "Data entity type" disp)
              [:span (get-in disp [:attrs :name])]]])

   
(defn- attribute-element[attribute det]
  [re-com/h-box
   :children [(tutil/name-and-id "Attribute" attribute)
              [:span {:class "template-attr"} (get-in attribute [:attrs :name])] 
              [:span {:class "left-margin right-margin"} "of"] 
              [:span {:class "template-det"} det]
              ]])

(defn- leattribute-element[attribute det]
  [re-com/h-box
   :children [(tutil/name-and-id "Link entity attribute" attribute)
              [:div [:span {:class "template-attr"} (get-in attribute [:attrs :name])] 
               " of " [:span  {:class "template-link"} det]]
              ]])

(defn- listdisplay-element[attribute]
  [re-com/h-box
   :children [(tutil/name-and-id "List display" attribute)
              [:div (str "Lazy count "(get-in attribute [:attrs :lazyCount]) 
                         " Lazy load " (get-in attribute [:attrs :lazyLoad]))]
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
               :children [(tutil/name-and-id "Style" style)
                          (tutil/unused-attrs style [:width :text-align :color 
                                                    :font-weight :font-size :line-height])
                          (tutil/attribute-span "Color" :color style)
                          (tutil/attribute-span "Font weight" :font-weight style)
                          (tutil/attribute-span "Font size" :font-size style)
                          (tutil/attribute-span "Line height" :line-height style)
                          (tutil/attribute-span "Width" :width style)
                          (tutil/attribute-span "Text align" :text-align style)
                          [buttons/info-button :info 
                           [:div {:class "template-examplebox" 
                                  :style (merge {:background-color "#eee"
                                                 :color "black"} 
                                                (select-keys (:attrs style) [:color :font-weight 
                                                                             :font-size :line-height
                                                                             :width :text-align]))} 
                            "The quick brown fox jumps over the lazy dog"]]]]
]])

(defn- linebreak-element []
  [re-com/h-box
   :children [[:div {:class "element-type"} "Line break"]
              ]])


(defn- eval-element [eval]
  [re-com/h-box
   :children [(tutil/name-and-id "Eval" eval)
              (condp = (get-in eval [:attrs :operator])
                "set" (list [:div[:span {:class "right-margin"} "When"]
                            [:span {:class "element-id right-margin"} (get-in eval [:attrs :elementId])]
                            [:span " is not empty"]])
                "notset" (list [:span {:class "right-margin"} "When"]
                            [:span {:class "element-id right-margin"} (get-in eval [:attrs :elementId])]
                            [:span "is empty"])
                "notequals" (list [:span {:class "right-margin"} "When"]
                            [:span {:class "element-id right-margin"} (get-in eval [:attrs :elementId])]
                            [:span {:class "right-margin"} "does not equal"]
                            [:span (get-in eval [:attrs :argument])])
                "equals" (list [:span {:class "right-margin"} "When"]
                            [:span {:class "element-id right-margin"} (get-in eval [:attrs :elementId])]
                            [:span {:class "right-margin"} "equals"]
                            [:span (get-in eval [:attrs :argument])])
                (list [:span {:class "right-margin"} (get-in eval [:attrs :operator])]
                      [:span {:class "element-id right-margin"} (get-in eval [:attrs :elementId])]
                      [:span {:class "element-id right-margin"} (str (:attrs eval))]
))]]
                )

(defn- paginator-element [eval]
  [re-com/h-box
   :children [(tutil/name-and-id "Paginator" eval)
              [:div 
               [:span {:class "right-margin"} "for"]
               [:span {:class "element-id"} (get-in eval [:attrs :for])]]]])

(defn- label-element [label]
  [re-com/h-box
   :children [(tutil/name-and-id "Label" label)
             [:span (str (get-in label [:attrs :bundleName]) "." 
                   (get-in label [:attrs :labelKey]))]]
   ])

(defn- link-element[link datamodel det]
  (let [link-name (get-in link [:attrs :name])
        other-side (tutil/other-side det (get-in link [:attrs :name]) datamodel)
        ]
    [re-com/h-box
     :children [(tutil/name-and-id "Link" link)
                [:div 
                 [:span {:style {:padding-right "5px"}} "Going from"]  
                 [:span {:class "template-det"} det] 
                 [:span " to "] 
                 [:span {:class "template-det"} other-side] 
                 [:span " via "] 
                 [:span {:class "template-link"} link-name]]
                ]]))

(def no-indent-elements ["column" "table"])

(defn display-template[template datamodel current-det & {:keys [no-indent] :or {no-indent false}}]
  (re-com/v-box 
   :class (if (or no-indent 
                  (not (nil? (some #{(:tag template)} no-indent-elements))))
            "no-indent-element"
            "element"
            )
   :children [
              (condp = (:tag template)
                "template" [:div 
                            [:div (str "Output template " (:det current-det))]
                            (display-parts template datamodel current-det)]
                "converisoutput" [:div 
                                  [:div (str "Output template " (:det current-det))]
                                  (display-parts template datamodel current-det)]
                "text" [:div (tutil/text-element template)
                        (display-parts template datamodel current-det)]
                "iot_attribute" [:div (attribute-element template (:det current-det))
                        (display-parts template datamodel current-det)]
                "relt_attribute" [:div (leattribute-element template (:let current-det))
                        (display-parts template datamodel current-det)]
                "iolink" [:div (iolink-element)
                        (display-parts template datamodel current-det)]
                "block" [:div (block-element)
                        (display-parts template datamodel current-det)]
                "relation" [:div (link-element template datamodel (:det current-det))
                        (display-parts template datamodel 
                                       (assoc current-det 
                                              :det (tutil/other-side (:det current-det) 
                                                          (get-in template [:attrs :name]) datamodel)
                                              :let (tutil/last-link (get-in template [:attrs :name]))))]
                "relationtype" [:div (link-element template datamodel current-det)
                        (display-parts template datamodel 
                                       (assoc current-det 
                                              :det (tutil/other-side (:det current-det) 
                                                               (get-in template [:attrs :name]) datamodel)
                                              :let (tutil/last-link (get-in template [:attrs :name]))))]
                "render" [:div (render-element)
                        (display-parts template datamodel current-det)]
                "eval" [:div (eval-element template)
                        (display-parts template datamodel current-det)]
                "table" [:div
                         [:div (tutil/name-and-id "Table" template)]
                         [:table {:class "template-table"} 
                         [:tbody
                          [:tr (doall (map #(vector :td 
                                                     (display-template %1 datamodel current-det :no-indent true))
                                            (:content template)))]]]]
                "style" [:div (style-element template)
                         (display-parts template datamodel current-det)]
                "column" [:div 
                          (display-parts template datamodel current-det :no-indent true)]
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
                "separateddisplay" [:div (separated-display-element template)
                         (display-parts template datamodel current-det)]
                "image" [:div (image-element template)
                         (display-parts template datamodel current-det)]
                "hline" [:div (simple-element template)
                         (display-parts template datamodel current-det)]
                "infoobjecttype" [:div (data-entity-type-element template)
                         (display-parts template datamodel current-det)]

                [:div {:style {:border "1px solid red"}}(str (:tag template) template)
                 (display-parts template datamodel current-det)]
                )
              ]))


