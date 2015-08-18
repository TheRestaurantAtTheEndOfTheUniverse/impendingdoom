(ns converis-lint.views.templateutil
    (:require [clojure.string :as str]
              [converis-lint.modelutils :as mutils]
              [reagent.core :as reagent]
              [clojure.zip :as zip]
              [re-com.buttons :as buttons]
              [re-com.core :as re-com]
              [re-frame.core :as re-frame] 
              [re-com.popover :as popover])

)

(defn md [icon title enabled]
  [:span [:i {:class "material-icons right-margin" 
              :style {:font-size "20px"
                      :color (if enabled "orange" "grey")}
              :title title} icon]]
)

(defn name-and-id [name elem]
  (list ^{:key "element-type"} [:span {:class "element-type right-margin"} name
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

(defn log [msg]
  (.log js/console msg)
)

(defn- other-side [det link-name datamodel]
  (if (string? link-name)
    (if (> (.indexOf link-name ",") -1)
      (recur det (str/split link-name ",") datamodel) 
      (let [link-type (mutils/get-link-entity-type datamodel link-name true)
            left (:left link-type)
            right (:right link-type)]
        (if (nil? link-type)
          nil
          (condp = det
            left right
            right left
            nil))))
    (let [new-start (other-side det (first link-name) datamodel)]
      (if (> (count link-name) 1)
        (recur new-start (rest link-name) datamodel)
        new-start
        ))))

(defn- other-side-or-unknown [det link-name datamodel]
  (let [other (other-side det link-name datamodel)]
    (if (nil? other)
      "unknown"
      other)
    ))

(defn last-link [link-name]
    (last (str/split link-name ",")))

(defn link-count [link-name]
    (count (str/split link-name ",")))

(defn- extra-info[source pairs & {:keys [width] :or {width "250px"}}]
  (if-not (empty? (select-keys source (keys pairs)))
    [buttons/info-button 
     :width width
     :info
     [:div {:class "extra-info"} 
      [:div "Extra info"] 
      [:table [:tbody
               (for [pair pairs]
                 (if-not (nil? (get source (key pair)))
                   [:tr 
                    [:td (val pair)] 
                    [:td (get source (key pair))]]))]]]]))

(defn error-info[errors]
  (if-not (and (empty? (:warnings errors))
               (empty? (:errors errors)))
  (let [showing? (reagent/atom false)]
        [popover/popover-anchor-wrapper
         :showing? showing?
         :position :right-center
         :anchor  [:span                    {
                     :on-mouse-over #(reset! showing? true)
                     :on-mouse-out  #(reset! showing? false)}
                   [:i {:class "material-icons right-margin" 
                              :style {:font-size "20px"
                                      :color  "orange"}
                              } "warning"]
                   ]
         :popover [popover/popover-content-wrapper
                   :showing? showing?
                   :position :right-center
                   :body     [re-com/v-box
                              :children [[:div (:errors errors)]
                                         [:div (:warnings errors)]
                                         ]
                              ]]])))


(defn text-element [text]
  [re-com/h-box
   :children [(name-and-id "Text" text)
              [:span (get-in text [:attrs :value])]
              (extra-info (:attrs text) {:textStyle "Style"})]])

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

(defn bool-attr [attr]
  (= "true" attr)
)


(defn attr-icon [element attribute icon enabled-text disabled-text]
  (if (not (nil? (get-in element [:attrs attribute])))
    (let [enabled (bool-attr (get-in element [:attrs attribute]))]
      (md icon (if enabled enabled-text disabled-text)
          enabled))))


(def template-type-weights
  {"STRING" 1
   "TEXT" 5
   "BOOLEAN" 0.1
   "NUMBER" 0.25
   "DATE" 0.35
   "CGV" 0.2}
)
