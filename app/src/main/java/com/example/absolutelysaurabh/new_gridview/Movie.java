package com.example.absolutelysaurabh.new_gridview;

/**
 * Created by absolutelysaurabh on 28/5/17.
 */

public class Movie {

    private String poster_url;
    private String title;
    private String overview;
    private String release_date;
    private int rating;

    public Movie(String poster_path_url, String title, String overview, String release_date, int rating){

        this.poster_url = poster_path_url;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.rating = rating;


    }

    public String getTitle(){

        return title;
    }

    public String getOverview(){

        return overview;
    }

    public int getRating(){

        return rating;
    }

    public String getPoster_path_url(){

        return poster_url;
    }

}
