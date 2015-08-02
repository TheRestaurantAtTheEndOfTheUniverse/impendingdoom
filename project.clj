(defproject converis-lint "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3211"]
                 [reagent "0.5.0"]
                 [re-frame "0.4.1"]
                 [re-com "0.5.4"]
                 [hickory "0.5.4"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [ring "1.4.0"]
                 [compojure "1.4.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.zip "0.1.1"]
                 [cljs-ajax "0.3.14"]
                 ]


  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.0.6"]
            [lein-figwheel "0.3.3" :exclusions [cider/cider-nrepl]]
            [lein-ring "0.8.8"]]

  :ring {:handler converis-lint.server/server}
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]

                        :figwheel {:on-jsload "converis-lint.core/mount-root"
                                   :http-server-root "public"
                                   :server-port 3449
                                   :ring-handler converis-lint.server/server}

                        :compiler {:main converis-lint.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main converis-lint.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :pretty-print false}}]}


  :profiles {:dev {
                   :source-paths ["src/clj"]
                   :plugins [[lein-figwheel "0.2.3-SNAPSHOT"]
                             ]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]
                              :ring-handler converis-lint.server/server}}}



)
