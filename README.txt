Felipe Oliveira
August 2011
Blog: http://geeks.aretotally.in
Twitter: http://twitter.com/_felipera

Play Framework Demo for Dreamforce 2011

-- Create Application --

play new dreamforce


-- Add dependency to CRUD module (conf/dependencies.yml) --

- play -> crud


-- Get Dependencies --

play dependencies


-- Generate Eclipse Project --
play eclipsify


-- Create Model (app/models/Tweet.java) --

package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * The Class Tweet.
 * 
 * @author Felipe Oliveira (@_felipera)
 */
@Entity
public class Tweet extends Model {

	/** The tweet. */
	@Required
	@MaxSize(140)
	public String tweet;

	/** The create date. */
	@Required
	public Date createDate = new Date();

	/**
	 * Find latest.
	 * 
	 * @return the list
	 */
	public static List<Tweet> findLatest() {
		return Tweet.find("order by createDate desc").fetch();
	}

	/**
	 * CRUD To String
	 * 
	 * @see play.db.jpa.JPABase#toString()
	 */
	@Override
	public String toString() {
		return this.tweet;
	}

}


-- Define DB for JPA Models (conf/application.conf)

Uncomment the following line:
"db=mem"


-- Add Controller Actions (app/controllers/Application.java) --

package controllers;

import java.util.List;

import models.Tweet;
import play.mvc.Controller;

/**
 * The Class Application.
 * 
 * @author Felipe Oliveira (@_felipera)
 */
public class Application extends Controller {

	/**
	 * Index.
	 */
	public static void index() {
		List<Tweet> tweets = Tweet.findLatest();
		render(tweets);
	}

	/**
	 * Creates the.
	 * 
	 * @param msg
	 *            the msg
	 */
	public static void create(String msg) {
		Tweet tweet = new Tweet();
		tweet.tweet = msg;
		tweet.save();
		render(tweet);
	}

	/**
	 * Tweets.
	 */
	public static void tweets() {
		List<Tweet> tweets = Tweet.findLatest();
		renderJSON(tweets);
	}

}


-- Define Main View (app/views/Application/index.html) --

#{extends 'main.html' /}
#{set title:'Home' /}

<!-- Create Tweet Form -->
<form>
	<input type="text" name="tweet" value="" />
	<input type="submit" value="Tweet">
</form>

<!-- Latest Tweets List -->
<ul>
	#{list tweets}
		<li>${_.tweet} (${_.createDate.since()})</li>
	#{/list}
</ul>

<!-- JS -->
<script type="text/javascript">

	// Capture Form Submit Event
	$('form').submit(function() {
		// Define Create Action
		var createAction = #{jsAction @create(':tweet') /}
		
		// Call Create Action
		$.post(createAction({tweet: $('input:first').val()}), function(data) {
			// Prepend Results to the List
			$('ul').prepend(data);
			$('input:first').val('');

		});
		
		// Don't let the browser redirect
		return false;
	});

</script>


-- Define Create Action View (app/views/Application/create.html) --

<!-- Single Item Render (after save action) -->
<li>${tweet.tweet} (${tweet.createDate.since()})</li>


-- Create Unit Test for Tweet Model --

import models.Tweet;

import org.junit.Assert;
import org.junit.Test;

import play.test.UnitTest;

/**
 * The Class BasicTest.
 * 
 * @author Felipe Oliveira (@_felipera)
 */
public class TweetTest extends UnitTest {

	/**
	 * Test model save.
	 */
	@Test
	public void testModelSave() {
		// Get Current Count
		long count = Tweet.count();

		// Save New Tweet
		Tweet t = new Tweet();
		t.tweet = "my sample tweet";
		t.save();

		// Get Count Afterwards
		long count2 = Tweet.count();

		// Second count should be the first count + 1
		Assert.assertEquals(count + 1, count2);
	}

}


-- Create CRUD Admin for Tweet Model --

package controllers;

/**
 * The Class Tweets.
 * 
 * @author Felipe Oliveira (@_felipera)
 */
public class Tweets extends CRUD {

}


-- Add Routes (conf/routes) --

*	/admin						module:crud
GET	/rest/tweets				Application.tweets


-- Define Messages for CRUD Admin (conf/messages)

tweet=Tweet
createDate=Date Created


Voila! Now Go Play!