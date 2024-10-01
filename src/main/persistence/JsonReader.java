package persistence;

import model.*;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;


/**
 * This class is adapted from the JsonReader class of JsonSerializationDemo.
 */
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads from file and reconstructs both MainList and Watchlist from the data;
    // throws IOException if an error occurs reading data from file
    public void read(MainList ml, Watchlist wl) throws IOException {
        // Clear the movies from both lists before parsing
        ml.clearMovies();
        wl.clearMovies();
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        parseWatchlist(wl, jsonObject.getJSONObject("watchlist"));
        parseMainList(ml, jsonObject.getJSONObject("mainList"));
    }



    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }



    // MODIFIES: wl
    // EFFECTS: parses watchlist from JSON object
    private void parseWatchlist(Watchlist wl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        wl.setName(name); // Assuming you have a setter for name in Watchlist
        addMovies(wl, jsonObject.getJSONArray("movies"));
    }

    // MODIFIES: ml
    // EFFECTS: parses main list from JSON object
    private void parseMainList(MainList ml, JSONObject jsonObject) {
        addMovies(ml, jsonObject.getJSONArray("movies"));
    }

    // MODIFIES: list (can be either MainList or Watchlist)
    // EFFECTS: parses movies from JSON array and adds them to the list
    private void addMovies(Object list, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextMovie = (JSONObject) json;
            addMovie(list, nextMovie);
        }
    }

    // MODIFIES: list (can be either MainList or Watchlist)
    // EFFECTS: parses movie from JSON object and adds it to the list
    private void addMovie(Object list, JSONObject jsonObject) {
        if (list instanceof MainList) {
            addMovieToMainList((MainList) list, jsonObject);
        } else {
            addMovieToWatchlist((Watchlist) list, jsonObject);
        }
    }

    // MODIFIES:MainList ml
    // EFFECTS: parses movie from JSON object and adds it to the mainList
    private void addMovieToMainList(MainList ml, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String genre = jsonObject.getString("genre");
        Movie movie = new Movie(title, genre);
        ml.addMovie(movie);
    }

    // MODIFIES:MainList ml
    // EFFECTS: parses movie from JSON object and adds it to the mainList
    private void addMovieToWatchlist(Watchlist wl, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String genre = jsonObject.getString("genre");
        Movie movie = new Movie(title, genre);
        wl.addMovie(movie);
    }
}