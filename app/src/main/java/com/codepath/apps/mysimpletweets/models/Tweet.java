package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ekok on 6/27/16.
 */

/* "text": "just another test",
    "contributors": null,
    "id": 240558470661799936,
    "retweet_count": 0,
    "in_reply_to_status_id_str": null,
    "geo": null,
    "retweeted": false,
    "in_reply_to_user_id": null,
    "place": null,
    "source": "<a href="//realitytechnicians.com%5C%22" rel="\"nofollow\"">OAuth Dancer Reborn</a>",
    "user": {
      "name": "OAuth Dancer",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_background_tile": true,
      "profile_sidebar_border_color": "C0DEED",
      "profile_image_url":"http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
      "created_at": "Wed Mar 03 19:37:35 +0000 2010",
      "location": "San Francisco, CA",
      "follow_request_sent": false,
      "id_str": "119476949",
      "is_translator": false,
      "profile_link_color": "0084B4",

*/

// Parse the JSON + Store the data, encapsulate state logic or display logic
public class Tweet {
    // List out the attributes
    private String body;
    private long uid; // unique id for the tweet
    private String createdAt;

    public User getUser() {
        return user;
    }

    private User user; // store embedded user object

    public String getCreatedAt() {
        return createdAt;
    }

    public long getUid() {
        return uid;
    }

    public String getBody() {
        return body;
    }

    // Deserialize the JSON and build Tweet objects
    // Tweet.fromJSON("{...}") => <Tweet>
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        // Extract the values from the json, store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return the tweet object
        return tweet;
    }

    // Tweet.fromJSONArray([ { ... }, { ... } ] => List<Tweet>)
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        // Iterate the json array and  create tweets
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        // Return the finished list
        return tweets;
    }

}
