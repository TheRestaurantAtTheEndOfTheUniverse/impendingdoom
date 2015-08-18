(ns converis-lint.data.checktemplate
    (:require [converis-lint.modelutils :as mutils])              
)

(defn merge-reports[r1 r2]
  (assoc r1 
         :erros (concat (:errors r1) (:errors r2))
         :warnings (concat (:warnings r1) (:warnings r2))))

(defn check-det [data-model det]
  (let [exists-matching-case (nil? (mutils/data-entity-type data-model 
                                                            det
                                                            false))
        uncased-det (mutils/data-entity-type data-model 
                                             det
                                             true)
        exists-ignore-case (not (nil? uncased-det))]
    (assoc {} 
           :errors (if-not exists-ignore-case 
                             [(str "Data entity type does not exist " det)])
           :warnings (if (and (not exists-ignore-case) exists-matching-case)
                       [(str "Case of data entity incorrect. Is " det 
                             " but should be " (:name uncased-det))])    
           )))

(defn check-det-attribute[data-model det attr-name]
  (if (keyword? det)
    (check-det-attribute data-model (name det) attr-name))
  (let [exists (not (nil? (mutils/data-entity-attribute data-model det attr-name)))]
    (assoc {} 
           :errors (if-not exists 
                     [(str "Data entity type " det " does not have attribute " attr-name)])
           )))


(defn log [msg]
  (.log js/console msg)
)

(defn check-let [data-model let-name current-det]
  (log (str "Check let " current-det let-name))
  (let [det (if-not (nil? current-det)
              (mutils/data-entity-type data-model current-det true))
        exists-matching-case (mutils/get-link-entity-type data-model 
                                            let-name
                                            false)
        uncased-link (mutils/get-link-entity-type data-model 
                                            let-name
                                            true)
        exists-ignore-case (not (nil? uncased-link))]
    (log (str (keyword let-name) exists-matching-case exists-ignore-case (:name uncased-link)))
    (log (str (mapv key (:linkentitytypes data-model))))
    (log (str (get (:linkentitytypes data-model) (keyword let-name))))
    (assoc {} 
           :errors (concat (if-not exists-ignore-case 
                             [(str "Link entity type does not exist " let-name)])
                           (if (and 
                                (not (nil? det))
                                exists-ignore-case
                                (not (or (= (:left uncased-link))
                                         (= (:right uncased-link)))))
                             [(str (:name det) " is not connected either side of " 
                                   (:name uncased-link))]))
           :warnings (concat (if (and exists-ignore-case (not exists-matching-case))
                               [(str "Case of link entity incorrect. Is " let-name 
                                     " but should be " (:name uncased-link))])    
                             (if (nil? det)
                               [(str "Location unknown")]))
           )))

(defn check-let-attribute[data-model let-name attr-name]
  (if (keyword? let-name)
    (check-let-attribute data-model (name let-name) attr-name))
  (let [link-entity-type (mutils/get-link-entity-type data-model let-name true)
        exists (not (nil? (mutils/link-entity-attribute data-model let-name attr-name)))]
    (assoc {} 
           :errors (if (nil? link-entity-type)
                     [(str "Link entity type does not exist " let-name)]
                     (if-not exists 
                       [(str "Link entity type " let-name " does not have attribute " attr-name)])
           ))))
