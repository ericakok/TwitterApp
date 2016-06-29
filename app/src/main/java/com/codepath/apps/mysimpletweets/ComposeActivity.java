package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();

        // Get the account info
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                // My current user account's info
                populateComposeTweet(user);
            }
        });

    }

    private void populateComposeTweet(User user) {
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvFullName = (TextView) findViewById(R.id.tvFullName);
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);

        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
        tvFullName.setText(user.getName());
        tvUsername.setText("@" + user.getScreenName());
    }

    public void postTweet(View view) {
        client.postStatusUpdate(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(getApplicationContext(), "Posted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void exitScreen(View view) {
        finish();
    }
}
