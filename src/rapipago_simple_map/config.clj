(ns rapipago-simple-map.config)

(defmacro api-url []
  (or (System/getenv "API_URL") "http://localhost:5000"))
