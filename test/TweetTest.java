/** 
 * Copyright 2011 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Felipe Oliveira (http://mashup.fm)
 * 
 */
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
