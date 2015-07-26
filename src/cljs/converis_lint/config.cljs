(ns converis-lint.config)

(def data-entity-hints 
  {:script-count {:summary "Scripts"
                  :description "Scripts make the save process very slow and are hard to maintail. Try to use as little scripts as possible."}
   :attribute-count {:summary "Attribute count"
                     :description "All attributes are loaded into the cache. Data entities with a lot of attributes will stress the cache and cause other objects to be evicted. Objects from the cache can be loaded 100 times faster so performance will be severly degraded."}
   :text-attribute {:summary "Text attribute"
                    :description "Text attributes can contain a lot of data, stress the cache and cause other objects to be evicted. Objects from the cache can be loaded 100 times faster so performance will be severly degraded."}
   :missing-description {:summary "Description"
                    :description "Please consider adding a description."}
   :data-size-too-large {:summary {:en "Data size too large"}
                         :description {:en "Data size too large"}}}   
)
