package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

/*
 * Represents a user's watchlist containing a list of movies.
 * Provides functions to add and remove movies from the watchlist,
 * ensuring no duplicate entries.
 * Each watchlist is identified by a unique name.
 */
public class Watchlist {
    private String name;
    private List<Movie> movies;
    private Watchlist testWatchlist;
    private Movie inception;
    private Movie matrix;


    // REQUIRES: name has a non-zero length
    // EFFECTS: name is initialized, movies list is initialized as empty
    public Watchlist(String name) {
        this.name = name;
        this.movies = new ArrayList<>();

    }

    // REQUIRES: movie is not null and exist in the MainList
    // MODIFIES: this
    // EFFECTS: adds a movie to the watchlist and returns error message if movie already exists
    public String addMovie(Movie movie) {
        for (Movie m : movies) {
            if (m.equals(movie)) {
                return "Error: Movie is already in the watchlist!";
            }
        }
        movies.add(movie);
        EventLog.getInstance().logEvent(new Event("Movie '" + movie.getTitle()
                +
                "' added to Watchlist '" + this.name + "'."));
        return "Movie added to watchlist successfully!";
    }

    // MODIFIES: this
    // EFFECTS: removes a movie from the watchlist and return error message if movie is not in the watchlist
    public String removeMovie(Movie movie) {
        for (Movie m : movies) {
            if (m.equals(movie)) {
                movies.remove(m);
                EventLog.getInstance().logEvent(new Event("Movie '" + movie.getTitle()
                        +
                        "' removed from Watchlist '" + this.name + "'."));
                return "Movie removed from watchlist successfully!";
            }
        }

        return "Error: Movie is not in the watchlist!";
    }


    public String getName() {
        return name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    // MODIFIES: this
    // EFFECTS: Sets the name of the watchlist
    public void setName(String name) {
        this.name = name;

    }
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name); // Assuming you have a field 'name'
        JSONArray moviesArray = new JSONArray();
        for (Movie m : movies) { // Assuming 'movies' is your list/collection of Movie objects
            moviesArray.put(m.toJson());
        }
        json.put("movies", moviesArray);
        return json;
    }

    public void clearMovies() {
        this.movies.clear();
    }


    // EFFECTS: returns the movie with the given title, or null if not found
    public Movie getMovieByTitle(String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null; // Return null if the movie is not found
    }


}
