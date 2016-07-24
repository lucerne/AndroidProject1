package com.example.lucerne.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucerne.flickster.R;
import com.example.lucerne.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by lucerne on 7/19/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    // View lookup cache
    private static class ViewHolder {
        ImageView image;
        TextView title;
        TextView overview;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    // Returns the number of types of Views that will be created by getView(int, View, ViewGroup)
    @Override
    public int getViewTypeCount() {
        // Returns the number of types of Views that will be created by this adapter
        // Each type represents a set of views that can be converted
        return Movie.popularityValues.values().length;
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup)
    // for the specified item.
    @Override
    public int getItemViewType(int position) {
        // Return an integer here representing the type of View.
        // Note: Integers must be in the range 0 to getViewTypeCount() - 1
        return getItem(position).getPopularity().ordinal();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        // Get the data item type for this position
        int type = getItemViewType(position);

        ViewHolder viewHolder; // view lookup cache stored in tag

        //Initiate container for inflate views from xml
        if (convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.poster_item_movie, parent, false);

            if (type == Movie.popularityValues.NOTPOPULAR.ordinal()) {
                convertView = inflater.inflate(R.layout.poster_item_movie, null);

                viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            } else {
                convertView = inflater.inflate(R.layout.backdrop_layout_movie, null);
            }

            viewHolder.image = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (type == Movie.popularityValues.NOTPOPULAR.ordinal()) {
            // Populate the data into the template view using the data object
            viewHolder.title.setText(movie.getOriginalTitle());
            viewHolder.overview.setText(movie.getOverview());
        }

        // clear out image from convertView
        viewHolder.image.setImageResource(0);

        // poster or backdrop in portrait mode
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            // popular movie backdrop in portrait mode
            if (getItemViewType(position) == Movie.popularityValues.NOTPOPULAR.ordinal()){
                Picasso.with(getContext()).load(movie.getPosterPath())
//                        .fit()
//                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .resize(0, 600)
                        .transform(new RoundedCornersTransformation(20, 10))
                        .into(viewHolder.image);

            }
            else{
                Picasso.with(getContext()).load(movie.getBackdropPath())
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .transform(new RoundedCornersTransformation(20, 10))
                        .into(viewHolder.image);
            }

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Picasso.with(getContext()).load(movie.getBackdropPath()).into(viewHolder.image);
            // popular movie backdrop in portrait mode
            if (getItemViewType(position) == Movie.popularityValues.NOTPOPULAR.ordinal()){
                Picasso.with(getContext()).load(movie.getPosterPath())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .resize(0, 600)
                        .transform(new RoundedCornersTransformation(20, 10))
                        .into(viewHolder.image);

            }
            else {
                Picasso.with(getContext()).load(movie.getBackdropPath())
                        .resize(0, 800)
                        .transform(new RoundedCornersTransformation(20, 10))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(viewHolder.image);
            }
        }
        return convertView;
    }


}
