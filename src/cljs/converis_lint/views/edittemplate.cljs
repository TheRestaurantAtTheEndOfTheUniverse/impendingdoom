(ns converis-lint.views.edittemplate
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


(defn- display-parts [elem datamodel current-det]
  (for [part (:content elem)]
    (display-template part datamodel current-det)))

(defn- attrs-display[element attrs]
  (map #(tutil/attribute-span %1 element) attrs)
)


(defn- section-selector[template]
  (let [sections (map #(get-in %1 [:attrs :name]) (:content template))]
        (.log js/console (str "T: " (mapv #(merge {:id %1 :label %1}) sections)))
        

    [re-com/single-dropdown
     :choices (mapv #(merge {:id %1 :label %1}) sections)
     :model (first sections)
     :on-change #()
     ])
)


(defn- section-element [section]
  [re-com/h-box
   :children [[:span {:class "element-type right-margin"} 
               (str "Section" (if (get-in section [:attrs :mandatory]) "*"
                                  ))]
              [:span {:class "right-margin"} (str "Heading " (get-in section [:attrs :heading]))]
              [:span {:class "right-margin"} (str "Name " (get-in section [:attrs :name]))]
              ]])

(defn- infobar-element [infobar]
  [re-com/h-box
   :children [(tutil/name-and-id "Infobar" infobar)
              (attrs-display infobar [:style :value])
              ]])

(defn- grid-element [grid]
  (let [unused (dissoc (:attrs grid)
                       :extendable :hideForIOT :name :tabsToRender)]
    [re-com/h-box
     :children [[:span {:class "element-type right-margin"} "Grid"
                 [:span {:class "element-id left-margin"} 
                  (if (get-in grid [:attrs :extendable])
                    "+"
                    "-")]]
                (if-not (empty? unused)
                  [:span (str unused)])
                (for [tab (str/split (get-in grid [:attrs :tabsToRender]) ",")]
                  [:span {:class "template-tab right-margin"} (str tab)]
                  )
                (attrs-display grid [:hideForIOT :name])
              ]]))

(defn- data-entity-type-element [element]
  (let [unused (dissoc (:attrs element)
                       :name)]
    [re-com/h-box
     :children [(tutil/name-and-id "Data entity type" element)
                (if-not (empty? unused)
                  [:span (str unused)])
                [:span {:class "template-det"} (get-in element [:attrs :name])] 
              ]]))


(defn- extra-attribute-info [attrs]
  [buttons/info-button :info
   [:div {:class "extra-info"} 
    [:div "Extra info"] 
    [:table [:tbody 
             [:tr 
              [:td "Field style"] 
              [:td (:fieldStyle attrs)]]
             [:tr 
              [:td "Text style"] 
              [:td (:textStyle attrs)]]
             [:tr 
              [:td "Filter values"] 
              [:td (if (true? (:filtervalues attrs)) 
                     "Yes" "No")]]
             ]]]])


(defn- attribute-element [element type type-class label]
  (let [unused (dissoc (:attrs element)
                       :name :mandatory :fieldStyle :textStyle :filtervalues)]
    [re-com/h-box
     :children [(tutil/name-and-id (str label (if (get-in element [:attrs :mandatory]) "*")) element)
                (if-not (empty? unused)
                  [:span (str unused)])
                [:span {:class "template-attr"} (get-in element [:attrs :name])] 
                [:span {:class "left-margin right-margin"} "of"] 
                [:span {:class type-class} type] 
                (extra-attribute-info (:attrs element))
              ]]))


(defn- link-element[link datamodel det]
  (let [unused (dissoc (:attrs link)
                       :name)
        link-name (get-in link [:attrs :name])
        other-side (tutil/other-side det (get-in link [:attrs :name]) datamodel)
        ]
    [re-com/h-box
     :children [(tutil/name-and-id "Link" link)
                [:div 
                (if-not (empty? unused)
                  [:span (str unused)])
                 [:span {:style {:padding-right "5px"}} "Going from"]  
                 [:span {:class "template-det"} det] 
                 [:span " to "] 
                 [:span {:class "template-det"} other-side] 
                 [:span " via "] 
                 [:span {:class "template-link"} link-name]]
                ]]))


(defn display-template[template datamodel current-det]
  [re-com/v-box 
   :class "element"
   :children [(condp = (:tag template)
                "template" [:div 
                            [:div (str "Edit template " (:det current-det))]
                            (section-selector template)
                            (display-template (first (:content template)) 
                                              datamodel current-det)] 
                "section" [:div (section-element template)
                           (display-parts template datamodel current-det)]
                "infobar" [:div (infobar-element template)
                           (display-parts template datamodel current-det)]
                "grid" [:div (grid-element template)
                           (display-parts template datamodel current-det)]
                "infoobjecttype" [:div (data-entity-type-element template)
                           (display-parts template datamodel current-det)]
                "iot_attribute" [:div (attribute-element template (:det current-det) 
                                                         "template-det" "Attribute")
                           (display-parts template datamodel current-det)]
                "text" [:div (tutil/text-element template)
                           (display-parts template datamodel current-det)]
                "relt_attribute" [:div (attribute-element template (:let current-det) 
                                                          "template-link" "Link entity attribute")
                           (display-parts template datamodel current-det)]
                "relationtype" [:div (link-element template datamodel (:det current-det))
                                (display-parts template datamodel  
                                               (assoc current-det 
                                                      :det (tutil/other-side (:det current-det) 
                                                                       (get-in template [:attrs :name]) datamodel)
                                                      :let (tutil/last-link (get-in template [:attrs :name]))))]
                              
                [:div {:style {:border "1px solid green"}}
                 (str (:tag template) (:attrs template))               
                 (display-parts template datamodel current-det)                  
                ])]])
