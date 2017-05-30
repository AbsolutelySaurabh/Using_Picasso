package com.example.absolutelysaurabh.new_gridview;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by absolutelysaurabh on 28/5/17.
 */

public class MovieQuery{

    public static final String LOG_TAG = MovieQuery.class.getSimpleName();

    private MovieQuery(){

    }

    public static List<Movie> fetchMovieData(String requestUrl){

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try{

            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){

            Log.e(LOG_TAG, " Problem making the HTTP request", e);

        }

        List<Movie> movie = extractFeatureFromJson(jsonResponse);

        return movie;

    }

    private static URL createUrl(String stringUrl){

        URL url = null;
        try{

            url = new URL(stringUrl);
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }

        return url;

    }

    private static String makeHttpRequest(URL url)throws IOException{

        String jsonResponse  = "";

        //if url is null then return here only
        if(url==null){

            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        //The inputSTream is needed to receive data in chunk
        InputStream inputStream = null;

        try{

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);//time in millisec
            urlConnection.setConnectTimeout(15000);//in millisec
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //if the response code was 200 that is successful
            if(urlConnection.getResponseCode()==200){

                inputStream = urlConnection.getInputStream();

                //Now read the data from the stream chunk
                jsonResponse = readFromSTream(inputStream);

            }else{

                Log.e(LOG_TAG, "Error Response code: "+ urlConnection.getResponseCode());

            }
        }catch(IOException e){

            Log.e(LOG_TAG, " Problem retrieving the moview JSON results. ",e);

        }finally {

            if(urlConnection==null){

                urlConnection.disconnect();

            }

            if(inputStream !=null){

                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromSTream(InputStream inputStream) throws IOException{

        //Use StringBuilder cz it's mutable and can be changed
        //unlike the normal String class in Java
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){

                output.append(line);
                line = reader.readLine();
            }

        }

        return output.toString();
    }

    private static List<Movie> extractFeatureFromJson(String jsonMovie){

        //checking that the jsonResponse is null or not
        if(TextUtils.isEmpty(jsonMovie)){

            return null;
        }

        List<Movie> movie = new ArrayList<Movie>();

        try{

            JSONObject baseJsonRsponse = new JSONObject(jsonMovie);

            JSONArray movieArray = baseJsonRsponse.getJSONArray("results");

            //traverse the JsonArray created
            for(int i=0;i<movieArray.length();i++){

                JSONObject currentMovie = movieArray.getJSONObject(i);

                String title = currentMovie.getString("original_title");

                String overview = currentMovie.getString("overview");

                String poster_url = currentMovie.getString("poster_path");

                String release_date = currentMovie.getString("release_date");

                int rating = currentMovie.getInt("vote_average");

                //Noe create the Movie Object

                Movie movies = new Movie(poster_url, title, overview, release_date, rating);
                movie.add(movies);

            }

        } catch (JSONException e) {

            Log.e("MovieQuery", " Problem parsing the movie JSON results ",e);

        }

        //finally return the list of movies
        return movie;
    }

}
