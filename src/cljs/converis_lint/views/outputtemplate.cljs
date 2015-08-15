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

(defn log [msg]
  (.log js/console msg)
)

(declare display-template)

(defn all-children [node]
   (take-while #(not (nil? %1))
              (iterate zip/right (zip/down node))))

(defn- display-parts [elem datamodel current-det & {:keys [no-indent] :or {no-indent false}}]
  (if (and (not (nil? elem))
           (zip/branch? elem)
           (not (nil? (zip/down elem))))
      (doall (map #(display-template %1 datamodel current-det :no-indent no-indent) 
                  (all-children elem)))))

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
              ^{:key "attr-name"}[:span {:class "template-attr"} (get-in attribute [:attrs :name])] 
              ^{:key "of"}[:span {:class "left-margin right-margin"} "of"] 
              ^{:key "attr-det"}[:span {:class "template-det"} det]
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
                                                    :font-weight :font-size :line-height
                                                     :padding :border :height])
                          (tutil/attribute-span "Color" :color style)
                          (tutil/attribute-span "Font weight" :font-weight style)
                          (tutil/attribute-span "Font size" :font-size style)
                          (tutil/attribute-span "Line height" :line-height style)
                          (tutil/attribute-span "Width" :width style)
                          (tutil/attribute-span "Text align" :text-align style)
                          (tutil/attribute-span "Padding" :padding style)
                          (tutil/attribute-span "Border" :border style)
                          (tutil/attribute-span "Height" :height style)
                          [buttons/info-button :info 
                           [:div {:class "template-examplebox" 
                                  :style (merge {:background-color "#eee"
                                                 :color "black"} 
                                                (select-keys (:attrs style) [:color :font-weight 
                                                                             :font-size :line-height
                                                                             :width :text-align
                                                                             :padding :border
                                                                             :height]))} 
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
                "set" (list ^{:key "When"}[:span {:class "right-margin"} "When"]
                            ^{:key "id"}[:span {:class "element-id right-margin"} (get-in eval [:attrs :elementId])]
                            ^{:key "cond"}[:span " is not empty"])
                "notset" (list ^{:key "When"}[:span {:class "right-margin"} "When"]
                               ^{:key "id"}[:span {:class "element-id right-margin"} (get-in eval [:attrs :elementId])]
                               ^{:key "cond"}[:span "is empty"])
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
  (let [node (zip/node template)]
  (re-com/v-box 
   :class (if (or no-indent 
                  (not (nil? (some #{(:tag template)} no-indent-elements))))
            "no-indent-element"
            "element"
            )
   :children [
              (condp = (:tag node)
                "template" [:div 
                            [:div (str "Output template " (:det current-det))]
                            (display-parts template datamodel current-det)]
                "converisoutput" [:div 
                                  [:div (str "Output template " (:det current-det))]
                                  (display-parts template datamodel current-det)]
                "text" [:div (tutil/text-element node)
                        (display-parts template datamodel current-det)]
                "iot_attribute" [:div (attribute-element node (:det current-det))
                        (display-parts template datamodel current-det)]
                "relt_attribute" [:div (leattribute-element node (:let current-det))
                        (display-parts template datamodel current-det)]
                "iolink" [:div (iolink-element)
                        (display-parts template datamodel current-det)]
                "block" [:div (block-element)
                        (display-parts template datamodel current-det)]
                "relation" [:div (link-element node datamodel (:det current-det))
                        (display-parts template datamodel 
                                       (assoc current-det 
                                              :det (tutil/other-side (:det current-det) 
                                                          (get-in node [:attrs :name]) datamodel)
                                              :let (tutil/last-link (get-in node [:attrs :name]))))]
                "relationtype" [:div (link-element node datamodel (:det current-det))
                        (display-parts template datamodel 
                                       (assoc current-det 
                                              :det (tutil/other-side (:det current-det) 
                                                               (get-in node [:attrs :name]) datamodel)
                                              :let (tutil/last-link (get-in node [:attrs :name]))))]
                "render" [:div (render-element)
                        (display-parts template datamodel current-det)]
                "eval" [:div (eval-element node)
                        (display-parts template datamodel current-det)]
                "table" [:div
                         [:div (tutil/name-and-id "Table" node)]
                         [:table {:class "template-table"} 
                         [:tbody
                          [:tr (doall 
                                (map #(vector :td 
                                              (display-template %1 datamodel current-det :no-indent true))
                                     (take-while #(not (nil? %1))
                                                 (iterate zip/right (zip/down template)))))]]]]
                "style" [:div (style-element node)
                         (display-parts template datamodel current-det)]
                "column" [:div 
                          (display-parts template datamodel current-det :no-indent true)]
                "label" [:div (label-element node)
                         (display-parts template datamodel current-det)]
                "linebreak" [:div (linebreak-element)
                         (display-parts template datamodel current-det)]
                "listdisplay" [:div (listdisplay-element node)
                         (display-parts template datamodel current-det)]
                "or" [:div (simple-element node)
                         (display-parts template datamodel current-det)]
                "and" [:div (simple-element node)
                         (display-parts template datamodel current-det)]
                "paginator" [:div (paginator-element node)
                         (display-parts template datamodel current-det)]
                "separateddisplay" [:div (separated-display-element node)
                         (display-parts template datamodel current-det)]
                "image" [:div (image-element node)
                         (display-parts template datamodel current-det)]
                "hline" [:div (simple-element node)
                         (display-parts template datamodel current-det)]
                "treedisplay" [:div (simple-element node)
                         (display-parts template datamodel current-det)]
                "infoobjecttype" [:div (data-entity-type-element node)
                         (display-parts template datamodel current-det)]

                [:div {:style {:border "1px solid red"}}(str (:tag node) node)
                 (display-parts template datamodel current-det)]
                )
              ])))


(def template-type-weights
  {"STRING" 1
   "TEXT" 5
   "BOOLEAN" 0.1
   "NUMBER" 0.25
   "DATE" 0.35
   "CGV" 0.2}
)

(def passthrough-elements 
  ["template"
   "converisoutput"
   "style"
   "render"
   "and"
   "or"
   "block"
   "linebreak"
   "listdisplay"
   "hline"
   "text"
   "separateddisplay"
   "column"
   "table"
   "iolink"
   "paginator"]
)

(def element-complexity
  { "template" 0
    "converisoutput" 0
    "text" 0
    "iot_attribute" 0
    "relt_attribute" 0
    "iolink" 0
    "block" 0
    "relation" 1
    "relationtype" 1
    "render" 0
    "eval" 0.5
    "table" 0
    "style" 0.5
    "column" 1
    "label" 0
    "linebreak" 0
    "listdisplay" 0
    "or" 0.2
    "and" 0.2
    "paginator" 2
    "separateddisplay" 0
    "image" 0
    "hline" 0
    "treedisplay" 0
    "infoobjecttype" 0}
)

(declare evaluate-template)

(defn evaluate-parts [template datamodel state]
  (reduce #(evaluate-template %2 datamodel %1)
          state
          (all-children template)))

(defn evaluate-template[template datamodel state]
  (let [node (zip/node template)
        complexity (get element-complexity (:tag node))]
    (condp = (:tag node)
      "label" (evaluate-parts template datamodel (assoc state 
                                                        :weight (+ 0.2 (:weight state))
                                                        :complexity (+ complexity (:complexity state))))
      "iot_attribute" (let [attr-name (get-in node [:attrs :name])
                            current-attr (mutils/data-entity-attribute datamodel 
                                                                       (:det state) attr-name)]
                        (evaluate-parts template datamodel 
                                        (assoc state 
                                               :weight (+ (get template-type-weights 
                                                               (:dataType current-attr)) 
                                                          (:weight state))
                                               :complexity (+ complexity (:complexity state))))
                        )
      "relt_attribute" (let [attr-name (get-in node [:attrs :name])
                            current-attr (mutils/link-entity-attribute datamodel 
                                                                       (:let state) attr-name)]                         
                         (evaluate-parts template datamodel 
                                        (assoc state 
                                               :weight (+ (get template-type-weights 
                                                               (:dataType current-attr)) 
                                                          (:weight state))
                                               :complexity (+ complexity (:complexity state)))))
      "eval" (evaluate-parts template datamodel (assoc state :eval (inc (:eval state))
                                                       :complexity (+ complexity (:complexity state))))
      "relation" (evaluate-parts template datamodel 
                                 (assoc state 
                                        :det (tutil/other-side (:det state) 
                                                               (get-in node [:attrs :name]) datamodel)
                                        :let (tutil/last-link (get-in node [:attrs :name]))
                                        :walk-depth (+ (tutil/link-count (get-in node [:attrs :name])) 
                                                       (:walk-depth state))
                                        :complexity (+ 0.5 (:complexity state))))
      "image" (evaluate-parts template datamodel (assoc state :weight (+ 5 (:weight state))))
      (do
        (if (nil? (some #{(:tag node)} passthrough-elements))
          (log (str "Unhandled: " (:tag node))))
        (evaluate-parts template datamodel (assoc state
                                                  :complexity (+ 0.5 (:complexity state)))))
      )
    ))


