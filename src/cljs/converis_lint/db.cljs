(ns converis-lint.db
  (:require [converis-lint.data.choicegroups :as choicegroups]
            [converis-lint.data.templates :as templates]
            [converis-lint.data.datamodel :as datamodel])
)

(def default-db
  {:stage :start-screen
   :choice-groups (mapv choicegroups/convert-choicegroup choicegroups/choicegroups)
   
  }
)


 
