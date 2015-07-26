(ns converis-lint.graph
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [re-com.core :as re-com]
              [re-com.selection-list :refer [selection-list-args-desc]]
              [re-com.buttons :as buttons]
              [converis-lint.config :as config]
              [converis-lint.graph.util :as graphutil]
              [converis-lint.db :as db]
              ))


(def EPSILON 0.000000001)
(def FORCEC 0.5)
(def OPTDIST 2)

(def component-width 680)
(def component-height 680)
(def padding 30)


(defn- maxsqrt [a b]
  (js/Math.max EPSILON (js/Math.sqrt (+ (* a a) (* b b))))
)

(defn repulse-nodes [n1 n2]
  (let [xdelta (- (:x n1) (:x n2))
        ydelta (- (:y n1) (:y n2))
        len (maxsqrt xdelta ydelta)
        ;force (/ (* FORCEC FORCEC) len)
        force (if (> len (* 400 OPTDIST))
                EPSILON
                (/ (js/Math.pow OPTDIST 4) len))
        xd (* (/ xdelta len) force)
        yd (* (/ ydelta len) force)
        ]
    (assoc n1 
           :xdisp (+ (:xdisp n1) xd)
           :ydisp (+ (:ydisp n1) yd)))
)

(defn attract-nodes [n1 n2]
  (let [xdelta (- (:x n1) (:x n2))
        ydelta (- (:y n1) (:y n2))
        len (maxsqrt xdelta ydelta)
        ;force (/ (* len len) FORCEC)
        force (/ (* len len) OPTDIST)
        xd (* (/ xdelta len) force)
        yd (* (/ ydelta len) force)
        ]
    [
     (assoc n1 
            :xdisp (- (:xdisp n1) xd)
            :ydisp (- (:ydisp n1) yd))
     (assoc n2 
            :xdisp (+ (:xdisp n2) xd)
            :ydisp (+ (:ydisp n2) yd))]))

(defn move-node[n temp]
  (let [delta (maxsqrt (:xdisp n) (:ydisp n))
        move-x (* (/ (:xdisp n) delta) (min delta temp))
        move-y (* (/ (:ydisp n) delta) (min delta temp))]
  (assoc n 
         :x (+ (:x n) move-x)
         :y (+ (:y n) move-y)
         :xdisp 0
         :ydisp 0)))


(defn repulse [all-nodes c]
  (reduce #(if (= (:id %2) (:id %1))
             %1
             (repulse-nodes %1 %2))
          (assoc c :xdisp 0 :ydisp 0)
          all-nodes))

(defn- attract-rec   [nodes edges] 
  (let [current-edge (first edges)
        left (get nodes (:n1 current-edge))
        right (get nodes (:n2 current-edge))
        [n1 n2] (attract-nodes left right)
        next-state (assoc nodes (:id n1) n1 (:id n2) n2)]
    (if (< (count edges) 2)
      (vals next-state)
    (recur next-state (rest edges)))))

(defn attract[graph]
    (if (= (count (:edges graph)) 0)
      (:nodes graph)
      (let [nodes (reduce #(assoc %1 (:id %2) %2)
                      {}
                      (:nodes graph))]
        (attract-rec nodes (:edges graph))
      ))
)

(defn layout-iteration [graph temp]
  (let [new-nodes (map #(repulse (:nodes graph) %1) 
                             (:nodes graph))
        repulse-calculated (assoc graph :nodes new-nodes)
        attract-calculated (mapv  #(move-node %1 temp) 
                                 (attract repulse-calculated))
        ]
    (assoc graph :nodes attract-calculated 
           :iteration (- (:iteration graph) 1))))

(defn fr-layout[graph temp]
    (layout-iteration graph temp)
)


(defn updater[]

  (let [data-model-graph (re-frame/subscribe [:data-model-graph])
        temp (:temp @data-model-graph)
        next-iteration (layout-iteration @data-model-graph temp)]
    (.log js/console (str "Updater " (:iteration @data-model-graph)))    
                                        ;(.log js/console (str "Temp " temp))
                                        ;(.log js/console (str "New state" (:nodes next-iteration)))
    (re-frame/dispatch-sync [:data-model-graph (assoc next-iteration :temp (/ temp 1.1))])
    (if (> (:iteration @data-model-graph) 0)
      (js/setTimeout updater 30))
))

(js/setTimeout updater 1000)

(defn coordinate-transform[x y range-x range-y min-x min-y]
  [(if (= range-x 0)
     (+ padding (/ component-width 2))
     (+ padding (* (- x min-x) (/ component-width range-x))))
   (if (= range-y 0)
     (+ padding (/ component-height 2))
     (+ padding (* (- y min-y) (/ component-height range-y))))]
)


(defn graph-component[data-model-graph]
  (let [visible (re-frame/subscribe [:data-model-graph-visible-entities])
        nodes (:nodes data-model-graph)
        edges (:edges data-model-graph)
        xcoords (map :x nodes)
        ycoords (map :y nodes)
        xmin (apply min xcoords)
        xmax (apply max xcoords)
        ymin (apply min ycoords)
        ymax (apply max ycoords)
        node-lookup (graphutil/node-lookup-map data-model-graph)]
    (.log js/console (str "Nodes: " (map :id (:nodes data-model-graph))))

    [:svg {:width (str (+ component-width padding padding))
           :height (str (+ component-height padding padding))
           :id "canvas"
           :style {:outline "2px solid black"
                   :background-color "#fff"}}
     (for [edge edges]
       (let [node1 (get node-lookup (:n1 edge))
             node2 (get node-lookup (:n2 edge))
             [x1 y1] (coordinate-transform (:x node1) (:y node1)
                                           (- xmax xmin) (- ymax ymin) 
                                           xmin ymin)
             [x2 y2] (coordinate-transform (:x node2) (:y node2)
                                           (- xmax xmin) (- ymax ymin) 
                                           xmin ymin)]
         ^{:key (str (:n1 edge) (:n2 edge) (rand 10))}
         [:line {:x1 (str x1) :y1 (str y1) 
                 :x2 (str x2) :y2 (str y2)
                 :stroke "#ccc"}]
         )       
       )

      ;; (for [node (:nodes @data-model-graph)]
      ;;  (let [x (+ padding (* (- (:x node) xmin) (/ component-width (- xmax xmin))))
      ;;        y (+ padding (* (- (:y node) ymin) (/ component-height (- ymax ymin))))]
      ;;   ^{:key (str (:id node) "box")}[:rect {:x (- x 30) 
      ;;                                         :y (- y 5) 
      ;;                                         :height 10 :width 60 
      ;;                                         :fill-opacity "1"
      ;;                                         :fill "white"
      ;;                                         :stroke "black"}]))
     ;;(.log js/console (str xmin " " xmax " " ymin " " ymax))
     (for [node nodes]
       (let [[x y] (coordinate-transform (:x node) (:y node) 
                                        (- xmax xmin) (- ymax ymin) 
                                        xmin ymin)]
;         (.log js/console (str (:id node) " " x " " y))
       ^{:key (str (:id node) "text")}[:text {:x x 
                                              :y (+ y 3)
                                              :text-anchor "middle"
                                              :style {:font-size "8px"
                                                      }}
                                       (:id node)]
       ))
     ]))


(defn selectable-graph[]
  (let [data-model-graph (re-frame/subscribe [:data-model-graph])]
        [re-com/h-box
         :children [(graph-component @data-model-graph)
                    [re-com/selection-list
                     :choices (mapv #(merge {:id %1 :label %1}) 
                                    (sort-by #(clojure.string/upper-case %1) (:all-entities @data-model-graph)))
                     :model (set (mapv #(merge {:id %1 :label %1}) 
                                    (:included-entities @data-model-graph)));;(set (:included-entities @data-model-graph))
                     :as-exclusions? false
                     :max-height (str (+ component-height padding padding) "px")
                     :multi-select?  true
                     :disabled?      false
                     :required?      false
                     :label-fn :label
                     :on-change #(re-frame/dispatch 
                                  [:data-model-graph-visible-entities (map :id %)])
                     ]
                    ]]))


