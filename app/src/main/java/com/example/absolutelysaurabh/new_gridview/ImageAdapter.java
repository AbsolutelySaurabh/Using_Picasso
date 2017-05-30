package com.example.absolutelysaurabh.new_gridview;

/**
 * Created by absolutelysaurabh on 28/5/17.
 */


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Movie> {
    private Context context;

    public ImageAdapter(@NonNull Context context, List<Movie> movies) {

        super(context, 0,movies);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView=  convertView;
        if(listItemView==null){

            // Inflate is a system service that creates a View out of an XML layout.
            listItemView =  LayoutInflater.from(getContext()).inflate(R.layout.grid_item,parent,false);

        }

        //Find the movie of the given position in the list of movies
        Movie currentMovie = getItem(position);

        int voteAverage = currentMovie.getRating();

        String url = currentMovie.getPoster_path_url();

        //http://image.tmdb.org/t/p/w185/xbpSDU3p7YUGlu9Mr6Egg2Vweto.jpg

        String newUrl = "http://image.tmdb.org/t/p/w185/"+url;

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        Picasso.with(context).load(newUrl).resize(355,530).centerCrop().error(R.drawable.life).into(imageView);

        return listItemView;

    }

}