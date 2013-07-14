(ns tweets-counter.core
  (:gen-class))

(defn get-count
  [username target]
  (let [twitter (twitter4j.TwitterFactory/getSingleton)
        user (.showUser twitter username)]
    (cond
      (= target "tweets") (.getStatusesCount user)
      (= target "following") (.getFriendsCount user)
      (= target "followers") (.getFollowersCount user)
      (= target "favorites") (.getFavouritesCount user))))

(defn -main
  [& args]
  (println (get-count (first args) (second args))))

