package com.example.lucerne.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucerne.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailedActivity extends AppCompatActivity {
    Movie movie;
    ImageView movieImage;
    TextView title, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        movie = (Movie) getIntent().getSerializableExtra("MovieDetails");
        Log.d("DEBUG", movie.toString());

        movieImage = (ImageView) findViewById(R.id.ivMovieImage);
        title = (TextView) findViewById(R.id.tvTitle);
        overview = (TextView) findViewById(R.id.tvOverview);

        // display title and overview
        title.setText(movie.getOriginalTitle());
        overview.setText(movie.getOverview());
        movieImage.setImageResource(0);

        Picasso.with(this).load(movie.getBackdropPath())
//                        .fit()
//                        .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .resize(0, 600)
                .transform(new RoundedCornersTransformation(20, 10))
                .into(movieImage);
    }
}
