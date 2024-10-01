package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

/*
 *  Represents the main list of movies available within the application.
 *  This class manages and maintains the collection of available movies for
 *  users to add to their individual watchlist.
 */
public class MainList {
    private List<Movie> movies;

    // EFFECTS: creates a new, empty list of movies
    public MainList() {
        movies = new ArrayList<>();
    }

    // REQUIRES: movie is not null
    // MODIFIES: this
    // EFFECTS: adds a movie to the list and returns an error message if movie is already present

    public String addMovie(Movie movie) {
        for (Movie m : movies) {
            if (m.equals(movie)) {
                return "Error: Movie is already present in the list!";
            }
        }
        movies.add(movie);
        EventLog.getInstance().logEvent(new Event("Movie '" + movie.getTitle() + "' added to MainList."));
        return "Movie added successfully!";
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public int getMoviesSize() {
        return movies.size();
    }



    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray moviesArray = new JSONArray();
        for (Movie m : movies) {
            moviesArray.put(m.toJson());
        }
        json.put("movies", moviesArray);
        return json;
    }

    // MODIFIES: movies list
    // EFFECTS: Removes all of the elements from this list
    public void clearMovies() {
        this.movies.clear();
    }
}
