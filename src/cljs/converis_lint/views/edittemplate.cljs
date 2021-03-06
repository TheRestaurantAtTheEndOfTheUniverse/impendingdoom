(ns converis-lint.views.edittemplate
    (:require [clojure.string :as str]
              [converis-lint.modelutils :as mutils]
              [converis-lint.views.templateutil :as tutil]
              [converis-lint.data.checktemplate :as check]
              [reagent.core :as reagent]
              [clojure.zip :as zip]
              [re-com.buttons :as buttons]
              [re-com.core :as re-com]
              [re-frame.core :as re-frame])
)

(defn log [msg]
  (.log js/console msg)
)

(defn all-children [node]
   (take-while #(not (nil? %1))
              (iterate zip/right (zip/down node))))


(def element-complexity
  { "section" 1
    "grid" 0.5
    "column" 1})


(def passthrough-elements 
  []
)

(declare evaluate-template)

(defn- evaluate-parts [template datamodel state]
  (reduce #(evaluate-template %2 datamodel %1)
          state
          (all-children template)))

(defn evaluate-template [template datamodel state]
  (let [node (zip/node template)
        complexity (get element-complexity (:tag node))]
    (condp = (:tag node)
      "iot_attribute" (let [attr-name (get-in node [:attrs :name])
                            current-attr (mutils/data-entity-attribute datamodel 
                                                                       (:det state) attr-name)]
                        (evaluate-parts template datamodel 
                                        (assoc state 
                                               :weight (+ (get tutil/template-type-weights 
                                                               (:dataType current-attr)) 
                                                          (:weight state))
                                               :complexity (+ complexity (:complexity state))))
                        )
      "relt_attribute" (let [attr-name (get-in node [:attrs :name])
                            current-attr (mutils/link-entity-attribute datamodel 
                                                                       (:let state) attr-name)]                         
                         (evaluate-parts template datamodel 
                                        (assoc state 
                                               :weight (+ (get tutil/template-type-weights 
                                                               (:dataType current-attr)) 
                                                          (:weight state))
                                               :complexity (+ complexity (:complexity state)))))
      "relationtype" (evaluate-parts template datamodel 
                                 (assoc state 
                                        :det (tutil/other-side (:det state) 
                                                               (get-in node [:attrs :name]) datamodel)
                                        :let (tutil/last-link (get-in node [:attrs :name]))
                                        :walk-depth (+ (tutil/link-count (get-in node [:attrs :name])) 
                                                       (:walk-depth state))
                                        :complexity (+ 0.5 (:complexity state))))
      (do
        (if (nil? (some #{(:tag node)} passthrough-elements))
          (log (str "Unhandled: " (:tag node))))
        (evaluate-parts template datamodel (assoc state
                                                  :complexity (+ 0.5 (:complexity state)))))
      )
    ))



(declare display-template)

(defn current-section[]
  (let [section (re-frame/subscribe [:current-section])]
        @section)
)
(defn named-section[template name]
  (first (filter #(= (get-in (zip/node %1) [:attrs :name]) name) 
              (all-children template))))

(defn- display-parts [elem datamodel current-det & {:keys [no-indent] :or {no-indent false}}]
  (if (and (not (nil? elem))
           (zip/branch? elem)
           (not (nil? (zip/down elem))))
    (let [first-child (zip/down elem)]
      (doall (map #(display-template %1 datamodel current-det :no-indent no-indent) 
                  (take-while #(not (nil? %1))
                              (iterate zip/right first-child)))))))

(defn- attrs-display[element attrs]
  (map #(tutil/attribute-span %1 element) attrs)
)

(defn- extra-attribute-info [attrs]
  [buttons/info-button :info
   [:div {:class "extra-info"} 
    [:div "Extra info"] 
    [:table [:tbody 
             (if-not (nil? (:fieldStyle attrs))
               [:tr 
                [:td "Field style"] 
                [:td (:fieldStyle attrs)]])
             (if-not (nil? (:textStyle attrs))
               [:tr 
                [:td "Text style"] 
                [:td (:textStyle attrs)]])
             [:tr 
              [:td "Filter values"] 
              [:td (if (true? (:filtervalues attrs)) 
                     "Yes" "No")]]
             ]]]])

(defn- section-selector[template]
  (let [sections (map #(get-in %1 [:attrs :name]) (:content template))
        section (current-section)
        model-value {:id section :label section}
        choices (mapv #(merge {:id %1 :label %1}) sections)]
    (if-not (empty? sections)
      [re-com/single-dropdown
       :choices choices
       :model section
       :class "section-selector"
       :on-change #(re-frame/dispatch [:current-section %1])
       ])
    ))


(defn- section-element [section]
  [re-com/h-box
   :children [[:span {:class "element-type right-margin"} 
               (str "Section" (if (get-in section [:attrs :mandatory]) "*"
                                  ))
               [:span {:class "element-id left-margin"} (get-in section [:attrs :name])]]
              [:span {:class "right-margin"} (str "Heading " (get-in section [:attrs :heading]))]
              ]])

(defn- infobar-element [infobar]
  [re-com/h-box
   :children [(tutil/name-and-id "Infobar" infobar)
              [:span {:class "right-margin"} (get-in infobar [:attrs :value])]
              (tutil/extra-info (:attrs infobar) {:style "Style"})
              ]])

(defn- grid-element [grid]
    [re-com/h-box
     :children [[:span {:class "element-type right-margin"} 
                 (str "Grid"
                      (if (get-in grid [:attrs :mandatory]) "*"))
                 [:span {:class "element-id left-margin"} 
                  (if (get-in grid [:attrs :extendable])
                    "+"
                    "-")]
                 [:span {:class "element-id left-margin"} (get-in grid [:attrs :name])]]
                (tutil/unused-attrs grid [:extendable :hideForIOT :name 
                                          :tabsToRender :gridLabelKey :showCreateLink 
                                          :showEmptyFilePanel :showUploadLink :attributeLayout
                                          :mandatory])
                [:span (for [tab (str/split (get-in grid [:attrs :tabsToRender]) ",")]
                  [:span {:class "template-tab right-margin"} (str tab)]
                  )]
                (tutil/attr-icon grid :showCreateLink "add_circle" 
                                 "Show create link" "Do not show create link")
                (tutil/attr-icon grid :showEmptyFilePanel "folder" 
                                 "Show empty file panel" "Do not show empty file panel")
                (tutil/attr-icon grid :showUploadLink "file_upload" 
                                 "Show upload link" "Do not show upload link")
                (tutil/attr-icon grid :attributeLayout "launch" 
                                 "Attribute layout" "No attribute layout")
                (attrs-display grid [:hideForIOT])
                [:span (get-in grid [:attrs :gridLabelKey])]
              ]])

(defn- rel-sequence-element [element]
    [re-com/h-box
     :children [[:span {:class "element-type right-margin"} "Sequence"
                 [:span {:class "element-id left-margin"} 
                  (str (if-not (get-in element [:attrs :editable])
                    "not ") "editable")]]
                (tutil/unused-attrs element [:editable :style])
                (attrs-display element [:style])
                ]])

(defn- data-entity-type-element [element]
  (let [unused (dissoc (:attrs element)
                       :name)]
    [re-com/h-box
     :children [(tutil/name-and-id "Data entity type" element)
                (if-not (empty? unused)
                  [:span (str unused)])
                [:span {:class "template-det"} (get-in element [:attrs :name])] 
              ]]))


(defn- attribute-element [element type type-class label]
  [re-com/h-box
   :children [(tutil/name-and-id (str label (if (get-in element [:attrs :mandatory]) "*")) element)
              (tutil/unused-attrs element [:name :mandatory :fieldStyle :textStyle :filtervalues :actionType])
              [:span {:class "template-attr"} (get-in element [:attrs :name])] 
              [:span {:class "left-margin right-margin"} "of"] 
              [:span {:class type-class} type] 
              (if-not (nil? (get-in element [:attrs :actionType]))
                [:span {:class "left-margin"} (str "Action " (get-in element [:attrs :actionType]))]) 
              (extra-attribute-info (:attrs element))
              ]])


(defn- attribute-group-element [element]
  [re-com/h-box
   :children [(tutil/name-and-id "Attribute group" element)
              (tutil/unused-attrs element [:style])
              (attrs-display element [:style])
              ]])

(defn check-link-element [link data-model det-name]
  (let [link-name (get-in link [:attrs :name])]
    (check/check-let data-model link-name det-name)))

(defn- link-element[link datamodel det]
  (let [unused (dissoc (:attrs link)
                       :name :showEditIOLink :parentonleftside 
                       :relateLeadIns :defaultTemplateSelector
                       :showDeleteLink)
        link-name (get-in link [:attrs :name])
        other-side (tutil/other-side-or-unknown det (get-in link [:attrs :name]) datamodel)
        errors (check-link-element link datamodel det)
        ]
    [re-com/h-box
     :children [(tutil/error-info errors)
                (tutil/name-and-id "Link" link)
                [:div 
                (if-not (empty? unused)
                  [:span (str unused)])
                 (tutil/attr-icon link :showEditIOLink "create" 
                                  "Show edit entity link" "Do not show edit entity link")
                 
                 (if (not (nil? (get-in link [:attrs :parentonleftside])))
                   (let [right (= "right" (get-in link [:attrs :parentonleftside]))]
                     (tutil/md (if right "arrow_back" "arrow_forward") 
                               (if right "Right to left" "Left to right")
                               true)))
                 
                 (tutil/attr-icon link :relateLeadIns "link" 
                                  "Relate lead ins" "Do not related lead ins")
                 
                 (tutil/attr-icon link :showDeleteLink "content_cut" 
                                  "Allow delete" "Do not allow delete")
                 
                 [:span {:style {:padding-right "5px"}} "Going from"]  
                 [:span {:class "template-det"} det] 
                 [:span " to "] 
                 [:span {:class "template-det"} other-side] 
                 [:span " via "] 
                 [:span {:class "template-link"} link-name]
                 (if-not (nil? (get-in link [:attrs :defaultTemplateSelector]))
                   [:span {:class "left-margin"} 
                    (str "Default type "
                         (get-in link [:attrs :defaultTemplateSelector]))])]]]))

(defn label-element [label]
  [re-com/h-box
   :children [(tutil/name-and-id "Label"label)
              [:span (str (get-in label [:attrs :bundleName]) "." 
                   (get-in label [:attrs :labelKey]))]
              (tutil/extra-info (:attrs label) {:textStyle "Style"})
              ]])

(defn table-element[table]
  [re-com/h-box
   :children [[:span (tutil/name-and-id "Table" table)]
              (tutil/unused-attrs table [:actionColumnStyle :style])
              (tutil/extra-info (:attrs table) {:actionColumnStyle "Action column style"
                                          :style "Style"} :width "500px")]]
)

(def no-indent-elements ["column" "colhead" "section"])

((defn display-template[template datamodel current-det & {:keys [no-indent] :or {no-indent false}}]
   (let [node (zip/node template)]
     [re-com/v-box 
      :class (if (or no-indent 
                     (not (nil? (some #{(:tag node)} no-indent-elements))))
               "no-indent-element"
               "element"
               )
      :children [(condp = (:tag node)
                   "template" [:div 
                               [:div (str "Edit template " (:det current-det))]
                               (section-selector node)
                               (let [current-section (named-section template (current-section))]
                                 (if-not (nil? current-section)
                                   (display-template current-section
                                                     datamodel current-det)))] 
                   "section" [:div (section-element node)
                              (display-parts template datamodel current-det)]
                   "infobar" [:div (infobar-element template)
                              (display-parts template datamodel current-det)]
                   "grid" [:div {:class "template-grid"}
                           (grid-element node)
                           (display-parts template datamodel current-det)]
                   "infoobjecttype" [:div (data-entity-type-element node)
                                     (display-parts template datamodel current-det)]
                   "iot_attribute" [:div (attribute-element node (:det current-det) 
                                                            "template-det" "Attribute")
                                    (display-parts template datamodel current-det)]
                   "iot_attr_group" [:div (attribute-group-element node)
                                    (display-parts template datamodel current-det)]
                   "text" [:div (tutil/text-element node)
                           (display-parts template datamodel current-det)]
                   "label" [:div (label-element node)
                            (display-parts template datamodel current-det)]
                   "filteredlist" [:div 
                                   [:span {:class "element-type right-margin"} "Filtered list"
                                    (if-not (nil? (get-in node [:attrs :name]))
                                      [:span {:class "element-id left-margin"} 
                                       (get-in node [:attrs :name])])]
                                   (display-parts template datamodel current-det)]
                   "relt_attribute" [:div (attribute-element node (:let current-det) 
                                                             "template-link" "Link attribute")
                                     (display-parts template datamodel current-det)]
                   "relationtype" [:div (link-element node datamodel (:det current-det))
                                   (display-parts template datamodel  
                                                  (assoc current-det 
                                                         :det (tutil/other-side-or-unknown (:det current-det) 
                                                                                (get-in node [:attrs :name]) datamodel)
                                                         :let (tutil/last-link (get-in node [:attrs :name]))))]
                   
                   "rel_sequence" [:div (rel-sequence-element node)
                                   (display-parts template datamodel current-det)]
                   "column" [:div 
                             [:div {:class "template-column"} (str "Style: "(get-in node [:attrs :style]))]   
                             (display-parts template datamodel current-det :no-indent true)]
                   "colhead" [:div {:class "template-colhead"}
                              (let [attrs (:attrs (zip/node (zip/down template)))]
                                (str (:bundleName attrs) "." (:labelKey attrs)))]
                   "table" [:div
                            (table-element node)
                            [:table {:class "template-table"} 
                             [:tbody
                             [:tr (doall 
                                   (map #(vector :td 
                                                 (display-template %1 datamodel current-det :no-indent true))
                                        (take-while #(not (nil? %1))
                                                    (iterate zip/right (zip/down template)))))]]]]
                   [:div {:style {:border "1px solid green"}}
                    (str (:tag node) (:attrs node))               
                    (display-parts template datamodel current-det)                  
                    ])]])))


