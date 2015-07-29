(ns converis-lint.views
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [re-com.core :as re-com]
              [re-com.buttons :as buttons]
              [converis-lint.config :as config]
              [converis-lint.views.datamodel :as datamodel]
              [converis-lint.db :as db]
              [converis-lint.graph.frlayout :as graph]
              [converis-lint.assessment :as asmt]
              [converis-lint.modelutils :as mutils])
)


(defn templates [current-entity])

(defn start-panel []
  (fn []
        (re-com/modal-panel
         :child [re-com/v-box
                 :width "300px"
                 :children [[re-com/title :label "Converis Lint"]
                            [re-com/gap :size "20px"]
                            [re-com/button
                             :label "Cancel"
                             :on-click #(re-frame/dispatch 
                                         [:stage :overview-screen])
                             ]]]
      )))


(defn modules[]
    (let [cg (re-frame/subscribe [:choice-groups])]
  [re-com/v-box
   :justify :center
   :align :start
   :padding "10px 30px 50px 70px"
   :style {:font-size "48px"}
   :children [[re-com/hyperlink :label "Data model explorer"
               :on-click #(re-frame/dispatch [:stage :data-model-explorer])]             
              [re-com/hyperlink :label "Data entity types"
               :on-click #(re-frame/dispatch [:stage :overview-screen])]
              [re-com/hyperlink :label "Link entity types" 
               :tooltip "Not available in the free version"
               :tooltip-position :right-center]
              [re-com/hyperlink :label "Choice groups" 
               :tooltip "Not available in the free version"
               :tooltip-position :right-center]
              [re-com/hyperlink :label "Rights" 
               :tooltip "Not available in the free version"
               :tooltip-position :right-center]
              [:div (str @cg)]
]])  
)




(defn main-panel []
  (fn []
    (let [stage (re-frame/subscribe [:stage])]
      [re-com/v-box
       :height "100%"
       :children [(if (= @stage :start-screen)
                    [modules])
                  (if (= @stage :data-model-explorer)
                    [datamodel/data-model-explorer])
                  (if (or (= @stage :overview-screen)
                          (= @stage :performance-screen))
                      [datamodel/main-screen])
                  ]]))
)


