package com.example.absolutelysaurabh.new_gridview;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by absolutelysaurabh on 28/5/17.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String LOG_TAG = MovieLoader.class.getSimpleName();

    private String mUrl;

    public MovieLoader(Context context, String url) {

        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();
    }


    @Override
    public List<Movie> loadInBackground() {

        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Movie> movies = MovieQuery.fetchMovieData(mUrl);
        return movies;
    }
}
