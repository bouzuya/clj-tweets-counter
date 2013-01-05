(ns tweets-counter.core
  (:require [twitter.oauth :as oauth]
            [twitter.api.restful :as twitter]
            [clojure.java.io :as jio]
            [clojure.string :as str])
  (:gen-class))

(defn- props
  [n]
  (let [u (jio/resource n)]
    (with-open [r (jio/reader u)]
      (doto
        (java.util.Properties.)
        (.load r)))))

(defn- props->map
  [^java.util.Properties props]
  (let [names (enumeration-seq (.propertyNames props))
        pairs (map (juxt keyword #(.getProperty props %)) names)]
    (into {} pairs)))

(defn -main
  [& args]
  (let [props (props->map (props "my.properties"))
        file (jio/file (props :filename))
        user (props :username)
        body (:body (twitter/show-user :params {:screen-name user}))
        formatter (java.text.SimpleDateFormat. "yyyy-MM-dd")
        now (.getTime (java.util.Calendar/getInstance))
        line (str/join "," [(.format formatter now)
                            (:statuses_count body)
                            (:followers_count body)
                            (:friends_count body)
                            (:favourites_count body)])]
    (when body
      (with-open [w (jio/writer file :append true)]
        (.write w line)
        (.newLine w)))))

