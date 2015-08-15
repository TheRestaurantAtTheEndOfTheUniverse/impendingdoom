(ns converis-lint.modelutils
    (:require [clojure.string :as str]
              [converis-lint.db :as db])
)

(def internal-attributes ["FShort description"
                          "Created by"
                          "Created on"
                          "Updated by"
                          "Updated on"
                          "Internal visible"
                          "Public visible"
                          "Short description"
                          "Status"
                          "intern manual"
                          "public manual"])

(defn is-internal-attribute[attr]
  ;; Check if the attribute is an internal one
  (not (nil? (some #{attr} internal-attributes)))
)

(defn log [msg]
  (.log js/console msg)
)

(defn keywordize[id]
  (if (keyword? id)
    id
    (keyword id)))

(defn unkeywordize[id]
  (if (keyword? id)
    (name id)
    id))

(defn data-entity [datamodel id] 
  (get (:dataentitytypes datamodel) (keywordize id)))

(defn data-entity-attribute [datamodel det-name attr-name]
  (let [det (data-entity datamodel det-name)
        attrs (:attributeDefinitions det)]
    (first (filter #(= attr-name (:name %1)) attrs))))

(defn data-entity-name [id]
  id
)

(defn data-entity-list [datamodel]
  (reduce #(conj %1 (assoc {} :id (key %2) :label (name (key %2))))
          []
          (:dataentitytypes datamodel)))

(defn get-link-entity-types [data-model]
  (vals (:linkentitytypes data-model))
)

(defn get-link-entity-type [data-model link-name ignore-case]
  (let [keyword-name (keywordize link-name)
        string-name (unkeywordize link-name)]
    (if ignore-case
      (let [matches (filter #(= (str/upper-case string-name)
                                (str/upper-case (:name (val %1))))
                            (:linkentitytypes data-model))          
            ]
        (if-not (empty? matches)
          (val (first matches))
          nil))
      (get (:linkentitytypes data-model) keyword-name)))
  )

(defn link-entity-attribute [datamodel link-name attr-name]
  (let [link (get-link-entity-type datamodel link-name false)
        attrs (:attributeDefinitions link)]
    (first (filter #(= attr-name (:name %1)) attrs))))


(defn connected-data-entities [db entity]
  (log (str "Connected " entity))
  (let [entity-name (name entity)]
  (reduce #(if (= entity-name (:left %2))
             (conj %1 (keyword (:right %2)))
             (if (= entity-name (:right %2))
               (conj %1 (keyword (:left %2)))
               %1))
          #{}
          (get-link-entity-types (:data-model db)))))

(defn- tree-cgv [root cgvs]
  (let [children (filter #(= (:name root) (:parentChoiceGroupValue %1)) cgvs)]
    ;(.log js/console (str "children of " (:name root) ": " (map :name children)))
    (if (empty? children)
      (select-keys root [:name :selectable :step] )
      (assoc root :children (map #(tree-cgv %1 cgvs) 
                                 (sort-by :step children))))))

(defn- tree-cgvs [cgvs]
  (let [roots (filter #(not (:parentChoiceGroupValue %1)) cgvs)]
    (.log js/console (str "roots: " roots))
    (map #(tree-cgv %1 cgvs) roots)))

(defn convert-choicegroup[cg]
  (.log js/console (str "Convert: " (:name cg)))
  (if (:tree cg)
    (assoc (select-keys cg [:name :tree]) :choice-group-values
           (tree-cgvs (:choiceGroupValues cg)))))

