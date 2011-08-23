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
