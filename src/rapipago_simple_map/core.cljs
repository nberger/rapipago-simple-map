(ns rapipago_simple_map.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! >! chan put! sliding-buffer]]
            [goog.events :as events])
  (:import [goog.net XhrIo]
           goog.net.EventType
           [goog.events EventType]))

(enable-console-print!)

(def map-bounds-chan (chan (sliding-buffer 1)))

(def gmap (js/GMaps. #js {:div "#map"
                          :zoom 14
                          :lat -34.603272
                          :lng -58.396726
                        }))

(.on gmap "bounds_changed" #(put! map-bounds-chan {:bounds (.getBounds gmap)
                                            :center (.getCenter gmap)}))

(def api-url "http://localhost:3001")

(defn json-xhr [{:keys [method url]}]
  (let [xhr (XhrIo.)
        out (chan)]
    (events/listen xhr goog.net.EventType.COMPLETE
                   (fn [e]
                     (put! out (js->clj (.getResponseJson xhr) :keywordize-keys true))))
    (. xhr (send url method))
    out))

(defn info-window [store]
  (str "<p>" (:name store) "</p>\n<p>" (:address store) "</p>"))

(defn build-marker [store]
  #js {:lat (get-in store [:location :lat])
       :lng (get-in store [:location :lon])
       :title (:name store)
       :infoWindow #js {:content (info-window store)}})

(defn process-stores [gmap stores]
  (doseq [store stores]
    (.addMarker gmap (build-marker store))))

(go
  (while true
    (let [{:keys [bounds center] :as e} (<! map-bounds-chan)
          top-right (.getNorthEast bounds)
          bottom-left (.getSouthWest bounds)
          url (str api-url
                   "/bounding_box/"
                   (.lat top-right) ","
                   (.lng top-right) ","
                   (.lat bottom-left) ","
                   (.lng bottom-left)
                   "/stores")
          stores-chan (json-xhr {:method "GET"
                                 :url url})
          stores (<! stores-chan)]
      (println (str "encontrados " (count stores) " stores"))
      (process-stores gmap stores))))

(go
  (while true
    (let [{:keys [bounds center] :as e} (<! map-bounds-chan)
          top-right (.getNorthEast bounds)
          bottom-left (.getSouthWest bounds)]
      (println top-right))))
