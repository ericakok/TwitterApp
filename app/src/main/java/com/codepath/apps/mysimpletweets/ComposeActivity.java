package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.Serializable;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;
    User user;

    private TextView tvCharCount;
    private EditText etTweet;

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

        tvCharCount = (TextView) findViewById(R.id.tvCharCount);
        etTweet = (EditText) findViewById(R.id.etTweet);

        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // this will show characters remaining
                tvCharCount.setText(140 - s.toString().length() + " ");
            }
        };

        etTweet.addTextChangedListener(watcher);

    }

    private void populateComposeTweet(User user) {
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvFullName = (TextView) findViewById(R.id.tvFullName);
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);

        Picasso.with(this).load(user.getProfileImageUrl()).transform(new RoundedCornersTransformation(2, 2)).into(ivProfileImage);
        tvFullName.setText(user.getName());
        tvUsername.setText("@" + user.getScreenName());
    }

    public void postTweet(View view) {
        EditText etTweet = (EditText) findViewById(R.id.etTweet);
        final String strTweet = etTweet.getText().toString();

        client.postStatusUpdate(strTweet, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Tweet tweet = Tweet.fromJSON(response);
                Intent data = new Intent();
                data.putExtra("status", (Serializable) tweet);
                setResult(RESULT_OK, data);
            }
        });
        finish();
    }

    public void exitScreen(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

}
