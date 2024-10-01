package model;

import persistence.Writable;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


/*
 * Represents a movie or a series with distinct title and genre.
 * This class serves as a data holder within the application, ensuring
 * each movie/series is characterized by its title and genre, and can be
 * represented as a string in a predefined format.
 */
public class Movie implements Writable {
    private String title;
    private String genre;

    // REQUIRES: title and genre have a non-zero length
    // MODIFIES: this
    // EFFECTS: title and genre are initialised
    public Movie(String title, String genre) { //String posterPath
        this.title = title;
        this.genre = genre;
        //this.posterPath = posterPath;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    // EFFECTS: returns a string representation of Movie with its title and genre
    @Override
    public String toString() {
        return title + " (" + genre + ")";
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("genre", genre);
        return json;
    }
}
