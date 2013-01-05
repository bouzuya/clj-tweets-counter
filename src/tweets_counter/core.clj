(ns tweets-counter.core
  (:require [clojure.java.io :as jio]
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
        username (props :username)
        twitter (twitter4j.TwitterFactory/getSingleton)
        user (.showUser twitter username)
        tweets-count (.getStatusesCount user)
        following-count (.getFriendsCount user)
        followers-count (.getFollowersCount user)
        favorites-count (.getFavouritesCount user)
        formatter (java.text.SimpleDateFormat. "yyyy-MM-dd")
        now (.getTime (java.util.Calendar/getInstance))
        line (str/join "," [(.format formatter now)
                            tweets-count
                            following-count
                            followers-count
                            favorites-count])]
    (with-open [w (jio/writer file :append true)]
      (.write w line)
      (.newLine w))))

