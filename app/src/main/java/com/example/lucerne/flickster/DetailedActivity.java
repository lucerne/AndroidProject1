package com.example.lucerne.flickster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lucerne.flickster.models.Movie;

public class DetailedActivity extends AppCompatActivity {
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        movie = (Movie) getIntent().getSerializableExtra("MovieDetails");
    }
}
