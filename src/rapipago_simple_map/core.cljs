(ns rapipago_simple_map.core
  )

(enable-console-print!)

(def map-options #js {
                      :div "#map"
                      :zoom 14
                      :lat -34.603272
                      :lng -58.396726
                      :click #(js/alert "click")
                      :dragend #(js/alert (str "dragend" %))
                      })

(def gmap (js/GMaps. map-options))
