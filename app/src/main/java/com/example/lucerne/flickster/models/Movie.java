package com.example.lucerne.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lucerne on 7/19/16.
 */
public class Movie implements Serializable{

    public enum popularityValues {
        NOTPOPULAR, POPULAR
    }

    String posterPath;
    String originalTitle;
    String overview;
    String backdropPath;
    // rating of more than 5 is popular
    popularityValues popularity;

    public String getOverview() {
        return overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public popularityValues getPopularity() {
        return popularity;
    }


    public Movie(JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");

        float p = Float.parseFloat(jsonObject.getString("vote_average"));
        if (p > 5){
            this.popularity = popularityValues.POPULAR;
        }
        else{
            this.popularity = popularityValues.NOTPOPULAR;
        }
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++){
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
