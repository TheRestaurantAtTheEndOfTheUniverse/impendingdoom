(ns converis-lint.db
  (:require [converis-lint.data.choicegroups :as choicegroups]
            [converis-lint.data.templates :as templates]
            [converis-lint.data.datamodel :as datamodel])
)

(def default-db
  {:stage :start-screen
   :data-model datamodel/datamodel
   :choice-groups (mapv choicegroups/convert-choicegroup choicegroups/choicegroups)
   :current-data-entity "Publication"
   :templates (group-by :dataEntityType templates/templates)
  }
)

 
