package persistence;

import model.*;

import org.json.JSONObject;


import java.io.*;

/**
 * This class is adapted from the JsonWriter class of JsonSerializationDemo.
 */
// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this, destination file
    // EFFECTS: writes JSON representation of MainList and Watchlist to destination file
    public void writeDatabase(MainList ml, Watchlist wl) {
        JSONObject json = new JSONObject();
        json.put("mainList", ml.toJson());
        json.put("watchlist", wl.toJson());
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
