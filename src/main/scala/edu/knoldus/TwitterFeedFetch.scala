package edu.knoldus

import com.typesafe.config.ConfigFactory
import twitter4j._
import twitter4j.conf.ConfigurationBuilder
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class TwitterFeedFetch {

  /**
    * Creating a file named Application.config in resources
    */

  val cn = ConfigFactory.load()
  val consumerKey: String = cn.getString("consumerKey")
  val consumerSecretKey: String = cn.getString("consumerSecretKey")
  val accessToken: String = cn.getString("accessToken")
  val accessTokenSecret: String = cn.getString("accessTokenSecret")

  val configurationBuilder: ConfigurationBuilder = new ConfigurationBuilder()
  configurationBuilder.setDebugEnabled(true)
    .setOAuthConsumerKey(consumerKey)
    .setOAuthConsumerSecret(consumerSecretKey)
    .setOAuthAccessToken(accessToken)
    .setOAuthAccessTokenSecret(accessTokenSecret)

  /**
    * can be used to Fetch per Tweet data
    */

  /*def getResult(stats: Status) = {
    val userName=stats.getUser.getName
    val status=stats.getText)
    val noOfRetweets= stats.getRetweetCount
    val noOfLikes=stats.getFavoriteCount
  }
*/

  /**
    * Fetches the feeds from Twitter
    *
    * @return List of Feeds From Twitter
    */

  def fetchTweets: Future[List[Status]] = Future {
    val twitter: Twitter = new TwitterFactory(configurationBuilder.build()).getInstance()
    val query = new Query("#scala")
    query.setCount(cn.getString("count").toInt)
    // taking count from config file because of warning of magic Number in scalastyle
    val list = twitter.search(query)
    val tweets: List[Status] = list.getTweets.asScala.toList
    tweets
  }

  /**
    * Gets the feeds and calculates average likes
    * @return average of likes
    */
  def averageLikes: Future[Int] = Future {
    val twitter: Twitter = new TwitterFactory(configurationBuilder.build()).getInstance()
    val query = new Query("#scala")
    query.setCount(cn.getString("count").toInt)
    val list = twitter.search(query)
    val feeds: List[Status] = list.getTweets.asScala.toList
    val likesCount = feeds.map(y => y.getFavoriteCount)
    likesCount.sum / 100
  }

  /**
    * Gets the feeds and calculates average retweets
    * @return average of retweets
    */
  def averageRetweets: Future[Int] = Future {
    val twitter: Twitter = new TwitterFactory(configurationBuilder.build()).getInstance()
    val query = new Query("#scala")
    query.setCount(cn.getString("count").toInt)
    val list = twitter.search(query)
    val feeds: List[Status] = list.getTweets.asScala.toList
    val retweetCount = feeds.map(y => y.getRetweetCount)
    retweetCount.sum / 100
  }

}
