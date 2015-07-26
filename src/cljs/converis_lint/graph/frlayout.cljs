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
(def OPTDIST 2)

(def component-width 680)
(def component-height 680)
(def padding 30)


(defn- maxsqrt [a b]
  (js/Math.max EPSILON (js/Math.sqrt (+ (* a a) (* b b))))
)

(defn repulse-nodes [n1 n2]
  ;; Repulse n1 from influence by n2
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
  ;; Move nodes after both repulsive
  ;; and attractive froces are calculated
  (let [delta (maxsqrt (:xdisp n) (:ydisp n))
        move-x (* (/ (:xdisp n) delta) (min delta temp))
        move-y (* (/ (:ydisp n) delta) (min delta temp))]
  (assoc n 
         :x (+ (:x n) move-x)
         :y (+ (:y n) move-y)
         :xdisp 0
         :ydisp 0)))

(defn repulse [all-nodes c]
  ;; Walk through all nodes and
  ;; calculate repulsive forces
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
  ;; Calculate attraction between all connected nodes
  (if (= (count (:edges graph)) 0)
    (:nodes graph)
    (let [nodes (reduce #(assoc %1 (:id %2) %2)
                        {}
                        (:nodes graph))]
      (attract-rec nodes (:edges graph))
      )))

(defn layout-iteration 
  ;; Do just one iteration
  ([graph temp]
   (layout-iteration graph temp 0))
  ;; Recursively do layout iterations
  ;; cooling by a factor of 1.1 every run
  ([graph temp iterations]
   (let [new-nodes (map #(repulse (:nodes graph) %1) 
                        (:nodes graph))
         repulse-calculated (assoc graph :nodes new-nodes)
         attract-calculated (mapv  #(move-node %1 temp) 
                                   (attract repulse-calculated))
         ]
     (if (< iterations 1)
       (assoc graph :nodes attract-calculated)
       (recur (assoc graph :nodes attract-calculated)  (/ temp 1.1) (dec iterations))
       ))))

(defn save-positions[graph]
  ;; Save positions before starting layout
  ;; Positions will be interpolated when drawing
  (assoc graph :nodes (mapv #(assoc %1 :x-orig (:x %1)
                                    :y-orig (:y %1)) (:nodes graph))))


(defn bounding-box[nodes xkey ykey]
  ;; Calculate bounding box for nodes
  ;; Can be used for final and original 
  ;; positions
  (let [xcoords (concat (map #(get %1 xkey) nodes))
        ycoords (concat (map #(get %1 ykey) nodes))
        xmin (apply min xcoords)
        xmax (apply max xcoords)
        ymin (apply min ycoords)
        ymax (apply max ycoords)]
    [xmin ymin xmax ymax])
)

(defn fr-layout[graph temp iterations]
  ;; Fruchtermann Reingold layout algorithm
  (layout-iteration (save-positions graph) temp 5)
)

(defn updater[]
  ;; Updated loop
  ;; Reschedules itself until iterations is 0
  ;; Sync dispatches graph changes so it is 
  ;; repainted
  (let [data-model-graph (re-frame/subscribe [:data-model-graph])
        iteration (:iteration @data-model-graph)]
    (re-frame/dispatch-sync [:data-model-graph (assoc @data-model-graph :iteration (dec iteration))])
    (if (> iteration 0)
      (js/setTimeout updater 20))))

(defn coordinate-transform[x y range-x range-y min-x min-y]
  [(if (= range-x 0)
     (+ padding (/ component-width 2))
     (+ padding (* (- x min-x) (/ component-width range-x))))
   (if (= range-y 0)
     (+ padding (/ component-height 2))
     (+ padding (* (- y min-y) (/ component-height range-y))))]
)

(defn animate-value 
  ([start end iteration total-frames]
   (let [mult (/ (- total-frames iteration) total-frames)
         dist (- end start)]
      (+ start (* dist mult))))
  ([start end fraction]
   (+ start (* (- end start) fraction)))) 

(defn animated-coordinates[node iteration total-frames]
  (if (= 0 iteration)
    [(:x node) (:y node)]
    (let [mult (/ (- total-frames iteration) total-frames)
          xdist (- (:x node) (:x-orig node))
          ydist (- (:y node) (:y-orig node))]
      [(+ (:x-orig node) (* xdist mult))
       (+ (:y-orig node) (* ydist mult))
       ]  
      )))
  
(defn graph-component[data-model-graph]
  (let [nodes (:nodes data-model-graph)
        edges (:edges data-model-graph)
        iteration (max (:iteration data-model-graph) 0)
        total-frames (:total-frames data-model-graph)
        mult (min 1 (/ (- total-frames iteration) total-frames))
        xcoords (map :x nodes)
        ycoords (map :y nodes)

        ;; Bounding box of current values
        [xmin-end ymin-end xmax-end ymax-end] (bounding-box (:nodes data-model-graph) :x :y) 
        ;; Bounding box of original values
        [xmin-start ymin-start xmax-start ymax-start] (if-not (nil? (:bounding-box data-model-graph))
                                                        (:bounding-box data-model-graph)
                                                        (bounding-box (:nodes data-model-graph) 
                                                                    :x-orig :y-orig))
        xmin (animate-value xmin-start xmin-end mult)
        xmax (animate-value xmax-start xmax-end mult)

        ymin (animate-value ymin-start ymin-end mult)
        ymax (animate-value ymax-start ymax-end mult)


        node-lookup (graphutil/node-lookup-map data-model-graph)]
    ;(.log js/console (str "Nodes: " (map :id (:nodes data-model-graph))))
    ;(.log js/console (str "Scale: " iteration " " total-frames))
    [:svg {:width (str (+ component-width padding padding))
           :height (str (+ component-height padding padding))
           :id "canvas"
           :style {:outline "1px solid black"
                   :background-color "#fff"}}
     (for [edge edges]
       (let [node1 (get node-lookup (:n1 edge))
             node2 (get node-lookup (:n2 edge))
             [x1s y1s] (animated-coordinates node1 iteration (:total-frames data-model-graph))
             [x1 y1] (coordinate-transform x1s y1s
                                           (- xmax xmin) (- ymax ymin) 
                                           xmin ymin)
             [x2s y2s] (animated-coordinates node2 iteration (:total-frames data-model-graph))
             [x2 y2] (coordinate-transform x2s y2s
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
     ;(.log js/console (str nodes))
     ;[:text {:x 10 :y 10} (str xmin "       " xmax)]
     ;[:text {:x 10 :y 30} (str xmin-start "      " xmin-end "       " mult)]
     (for [node nodes]
       (let [[xsource ysource] (animated-coordinates node iteration total-frames)
             [x y] (coordinate-transform xsource ysource 
                                        (- xmax xmin) (- ymax ymin) 
                                        xmin ymin)]
       ^{:key (str (:id node) "text")}[:text {:x x 
                                              :y (+ y 3)
                                              :text-anchor "middle"
                                              :style {:font-size "12px"
                                                      }}
                                       (:id node)]
       ))
     ]))


(defn selectable-graph[]
  (let [data-model-graph (re-frame/subscribe [:data-model-graph])]
        [re-com/h-box
         :padding "0 0 0 10px"
         :children [(graph-component @data-model-graph)
                    [re-com/selection-list
                     :choices (mapv #(merge {:id %1 :label %1}) 
                                    (sort-by #(clojure.string/upper-case %1) (:all-entities @data-model-graph)))
                     :model (set (mapv #(merge {:id %1 :label %1}) 
                                    (:included-entities @data-model-graph)))
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


