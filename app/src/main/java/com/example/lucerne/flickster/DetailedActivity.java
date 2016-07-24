package com.example.lucerne.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucerne.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailedActivity extends AppCompatActivity {
    Movie movie;
    ImageView movieImage;
    TextView title, overview, voteCount, releaseDate;
    ArrayList<ImageView> starImages;

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
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .resize(0, 600)
                .transform(new RoundedCornersTransformation(20, 10))
                .into(movieImage);

        // adding stars
        // should make starImage static or viewholder kind of type :(
        starImages = new ArrayList<ImageView>();
        starImages.add((ImageView) findViewById(R.id.ivStarImage1));
        starImages.add((ImageView) findViewById(R.id.ivStarImage2));
        starImages.add((ImageView) findViewById(R.id.ivStarImage3));
        starImages.add((ImageView) findViewById(R.id.ivStarImage4));
        starImages.add((ImageView) findViewById(R.id.ivStarImage5));

        float voteAverage = movie.getVoteAverage();

        // calculate number of stars
        int numberStars = 0;
        if (voteAverage > 6){
            numberStars = 5;
        }
        else if (voteAverage > 4){
            numberStars = 4;
        }
        else{
            numberStars = Math.round(voteAverage);
        }

        // draw stars
        int i=0;
        while (i < numberStars){
            Picasso.with(this).load(R.drawable.yellowstarfull)
                    .into(starImages.get(i));
            i += 1;
        }
        while (i < starImages.size()){
            Picasso.with(this).load(R.drawable.yellowstarempty)
                    .into(starImages.get(i));
            i += 1;
        }

        // display release date and ratings
        voteCount = (TextView) findViewById(R.id.tvVoteCount);
        releaseDate = (TextView) findViewById(R.id.tvReleaseDate);

        voteCount.setText("(" + Integer.toString(movie.getVoteCount()) + ")");
        releaseDate.setText(movie.getReleaseDate());
    }
}
