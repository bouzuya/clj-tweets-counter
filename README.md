# Tweets Counter

Tweets Counter is command-line utility to count tweets, followers, friends and favorites.

## Usage

    $ USERNAME=bouzuya
    $ # TARGET= tweets or following or followers or favorites
    $ TARGET=tweets
    $ java -Dtwitter4j.oauth.consumerKey=xxx \
           -Dtwitter4j.oauth.consumerSecret=xxx \
           -Dtwitter4j.oauth.accessToken=xxx \
           -Dtwitter4j.oauth.accessTokenSecret=xxx \
           -jar tweets-counter-0.2.0-standalone.jar $USERNAME $TARGET

## License

Copyright © 2013 bouzuya

Distributed under the Eclipse Public License, the same as Clojure.
