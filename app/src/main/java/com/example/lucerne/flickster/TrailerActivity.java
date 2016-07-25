package com.example.lucerne.flickster;

import android.os.Bundle;
import android.util.Log;

import com.example.lucerne.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TrailerActivity extends YouTubeBaseActivity {

    Movie movie;
    YouTubePlayer youTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        movie = (Movie) getIntent().getSerializableExtra("MovieDetails");
        Log.d("DEBUG", movie.toString());

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize("AIzaSyAs_APVkK4pArxgeIMNEJ1aDdx9lsCc6ks",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        // youTubePlayer.cueVideo("5xVh-7ywKpE");
                        fetchTrailerAsync(movie, youTubePlayer);
//                        youTubePlayer.loadVideo("QpNXHJGfGYo");
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    // Get trailer data
    public void fetchTrailerAsync(final Movie movie, final YouTubePlayer youTubePlayer) {

        String url ="http://api.themoviedb.org/3/movie/" + movie.getId() + "/videos";
//        String id = this.id;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray trailerJsonResults = null;

                try {
                    trailerJsonResults = response.getJSONArray("youtube");
                    String source = (trailerJsonResults.getJSONObject(0)).getString("source");
                    youTubePlayer.loadVideo(source);

//                    Log.d("DEBUG",  Boolean.toString(swipeContainer.isRefreshing()));
                    Log.d("DEBUG", source);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
