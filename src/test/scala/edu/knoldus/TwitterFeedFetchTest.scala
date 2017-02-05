package edu.knoldus

import org.scalatest.FunSuite
import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Class For Testing the Twitter Feeds
  */
class TwitterFeedFetchTest extends FunSuite {

  test("Testing Fetching of Feeds") {
    assert(!Await.result((new TwitterFeedFetch).fetchTweets, 10 seconds).isEmpty)
  }

  test("Testing Average Likes") {
    assert(Await.result((new TwitterFeedFetch).averageLikes, 10 seconds) == 0)
  }

  test("Testing Average Retweets") {
    assert(Await.result((new TwitterFeedFetch).averageRetweets, 10 seconds) == 2)
  }

}
