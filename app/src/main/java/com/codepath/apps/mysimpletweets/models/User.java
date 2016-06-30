package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/*

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

public class User implements Serializable {
    // List attributes

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagline;
    private int followersCount;
    private int followingCount;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return followingCount;
    }


    // deserialize the user json => User
    public static User fromJSON(JSONObject json) {
        User u = new User();
        // Extract and fill the values
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.tagline = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingCount = json.getInt("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return a user
        return u;
    }
}
