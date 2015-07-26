(ns converis-lint.views.datamodel
    (:require [re-frame.core :as re-frame]
              [re-com.core :as re-com]
              [re-com.buttons :as buttons]
              [converis-lint.config :as config]
              [converis-lint.db :as db]
              [converis-lint.assessment :as asmt]
              [converis-lint.model-utils :as mutils])
)

(defn- nav-header [current-entity]
  [re-com/h-box
   :justify :start
   :align :end
   :padding "5px 0px 5px 15px"
   :style {:background-color "#eee"}
   :children [
              [buttons/md-icon-button 
               :md-icon-name "md-home"
               :on-click #(re-frame/dispatch [:stage :start-screen])]
              [re-com/single-dropdown
               :choices (sort-by #(clojure.string/upper-case (:label %1)) 
                                 (mutils/data-entity-list))
               :model current-entity
               :width "300px"
               :on-change #(re-frame/dispatch [:current-data-entity %])
              ]
              [re-com/hyperlink :label "Overview"
               :class "navheader-button"
               :tooltip "Overview and Evaluation"
               :on-click #(re-frame/dispatch [:stage :overview-screen])]
              [re-com/hyperlink :label "Performance"
               :class "navheader-button"
               :tooltip "Performance related numbers"
               :on-click #(re-frame/dispatch [:stage :performance-screen])]
              [re-com/hyperlink :label "Templates"
               :class "navheader-button"
               :tooltip "Not available in the free version"]]])

(defn- score-evaluation[score]
  (condp < score
    90 "good"
    80 "ok"
    70 "bad"
    60 "very bad"
    50 "abysmal"
    30 "unusable")
)

(defn- score [hints] 
  (let [score (- 100 (apply + (map :weight hints)))
        evaluation (score-evaluation score)]
    [:div {:class "assessment"} "Overall assessment " 
     [:span {:class (clojure.string/replace evaluation #" " "_")} (str score " (" evaluation ")")]])
)

(defn- attr-row[attr row-class]
  (let [class (str row-class 
                   (if (mutils/is-internal-attribute (:name attr))
                     " internal-attribute"))]
    ^{:key (:name attr)} [:tr {:class class}
                          [:td (:name attr)]
                          [:td (:dataType attr)]
                          [:td (str (:editable attr))]
                          [:td (str (:multiLanguage attr))]
                          [:td (if (:editable attr)
                                 (do
                                   [:div {:style {:display "inline-flex"}}
                                    [re-com/md-icon-button :md-icon-name "md-edit"
                                     :tooltip "Not available in the free version"]
                                    [re-com/md-icon-button :md-icon-name "md-delete"
                                     :tooltip "Not available in the free version"]]))]
                          ])
)

(defn- attr-sorter[a1 a2]
  (let [internal1 (mutils/is-internal-attribute (:name a1))
        internal2 (mutils/is-internal-attribute (:name a2))
        ]
    (if (and internal1 (not internal2))
      -1
      (if (and (not internal1) internal2)
        1
        (compare (:name a1) (:name a2)))))
)

(defn- attr-table[attrs]
  (let [row-classes (cycle ["even-row" "odd-row"])]
    [:div
    [re-com/title :level :level1 :label "All attributes"]
    [:table
     [:tr {:class "header-row"}
      [:td "Name"]
      [:td "Data type"]
      [:td "Editable"]
      [:td "Multi language"]
      [:td  [re-com/md-icon-button :md-icon-name "md-add"
             :tooltip "Not available in the free version"]]
      ]
     [:tbody
      (doall (for [attr (map vector (sort attr-sorter attrs) row-classes)]
             (attr-row (first attr) (second attr))))]]])
)


(defn- attr-by-type-table[attrs]
  (let [by-type (group-by :dataType attrs)
        count-by-type (reduce #(assoc %1 (key %2) (count (val %2)))
                              {}
                              by-type)
        row-classes (cycle ["even-row" "odd-row"])]

    [:div
    [re-com/title :level :level1 :label "Attributes by data type"]
     [:table
      [:tbody
      [:tr {:class "header-row"}
       [:td "Data type"]
       [:td "Count"]
       ]
      
      (doall (for [type (map vector (sort-by key by-type) row-classes)]
               ^{:key type}[:tr {:class (second type)}
                            [:td (key (first type))]
                            [:td (count (val (first type)))]]))]]])
)

(defn- basic-info[current-entity]
  (let [entity-name (mutils/data-entity-name current-entity)
        model-info (mutils/data-entity entity-name)        
        attrs (:attributeDefinitions model-info)
        by-type (group-by :dataType attrs)
        assessment (asmt/assess model-info)
        ]
    [re-com/v-box
     :padding "0 0 0 10px"
     :children [
                (score assessment)
                [:div (str entity-name " is " 
                           (if (nil? (:typeSelector model-info)) "not ") "dynamic.")]
                [:div (str entity-name " has " (count attrs) " Attributes.")]
                [:div (str entity-name " is " 
                           (if-not (:editable model-info) "not ") "editable.")]
                [:div (str entity-name " has " (:maximumStatus model-info) " status steps.")]
                
                (attr-by-type-table attrs)
                (attr-table attrs)

                ]]    
  )
)

(defn- hint-row[hint row-class]
  (let [info (get config/data-entity-hints (:type hint))]
  [:tr {:class row-class}
   [:td (:summary info)]
   [:td (:location hint)]
   [:td [:div {:class "hint-col"}
    (:description info)]]
   ])
)


(defn- hint-table[hints]
  (let [row-classes (cycle ["even-row" "odd-row"])]
    [:div
    [re-com/title :level :level1 :label "Hints"]
    [:table
     [:tr {:class "header-row"}
      [:td "Summary"]
      [:td "Attribute"]
      [:td {:class "hint-col"} "Hint"]
      ]
     [:tbody
      (doall (for [hint (map vector hints row-classes)]
             (hint-row (first hint) (second hint))))]]])
)

(defn- performance [current-entity]
  (let [entity-name (mutils/data-entity-name current-entity)
        model-info (mutils/data-entity entity-name)    
        assessment (asmt/assess model-info)    
        ]
    [re-com/v-box
     :padding "0 0 0 10px"
     :children [
                [:div (str "Estimated weight: " (asmt/data-entity-weight model-info))]
                [:div (str "Estimated total weight: " "data not available via webservices")]
                (hint-table assessment)
                ]]))

(defn- data-entity-hint [weight summary text]
  [:tr
   [:td weight]
   [:td summary]
   [:td text]
   ]  
)


(defn- data-entity-hint-table [hints]
    [:table
     (doall 
      (for [object-hint hints]
        (let [hint-info (get config/data-entity-hints (:type object-hint))]
          (data-entity-hint (:weight object-hint)
                            (:en (:summary hint-info)) 
                            (:en (:description hint-info)))
          )))])

(defn main-screen [] 
  (let [current-data-entity (re-frame/subscribe [:current-data-entity])
        stage (re-frame/subscribe [:stage])]
   [re-com/v-box
    :size "auto"
    :justify :start 
    :children [[nav-header @current-data-entity]
               (if (= @stage :overview-screen)
                 [basic-info @current-data-entity])
               (if (= @stage :performance-screen)
                 [performance @current-data-entity])               
               ]]))
               
