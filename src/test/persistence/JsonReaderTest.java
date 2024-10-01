package persistence;

import model.MainList;
import model.Movie;
import persistence.*;

import model.Watchlist;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is adapted from the JsonReaderTest class of JsonSerializationDemo.
 */
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        MainList ml = new MainList();
        Watchlist wl = new Watchlist("Test");
        try {
            reader.read(ml, wl);
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDatabase() {
        JsonReader reader = new JsonReader("./data/testEmptyDatabase.json");
        MainList ml = new MainList();
        Watchlist wl = new Watchlist("Test");
        try {
            reader.read(ml, wl);
            assertEquals(0, ml.getMovies().size());
            assertEquals(0, wl.getMovies().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDatabase() {
        JsonReader reader = new JsonReader("./data/testGeneralDatabase.json");
        MainList ml = new MainList();
        Watchlist wl = new Watchlist("Test");
        try {
            reader.read(ml, wl);
            assertEquals(1, ml.getMovies().size());
            assertEquals(1, wl.getMovies().size());
            checkMovie("Inception", "Sci-Fi", ml.getMovies().get(0));
            checkMovie("RRR", "Action", wl.getMovies().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testAddMovieToMainList() {
        try {
            MainList ml = new MainList();
            ml.addMovie(new Movie("Inception", "Sci-Fi"));
            Watchlist wl = new Watchlist("Test");
            JsonWriter writer = new JsonWriter("./data/testAddMovieMainList.json");
            writer.open();
            writer.writeDatabase(ml, wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testAddMovieMainList.json");
            reader.read(ml, wl);
            assertEquals(1, ml.getMovies().size());
            assertEquals(0, wl.getMovies().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testAddMovieToWatchlist() {
        try {
            MainList initialMainList = new MainList();
            Watchlist initialWatchlist = new Watchlist("Test");
            initialWatchlist.addMovie(new Movie("Inception", "Sci-Fi"));
            JsonWriter writer = new JsonWriter("./data/testAddMovieWatchlist.json");
            writer.open();
            writer.writeDatabase(initialMainList, initialWatchlist);
            writer.close();
            MainList ml = new MainList();
            Watchlist wl = new Watchlist("Test");
            JsonReader reader = new JsonReader("./data/testAddMovieWatchlist.json");
            reader.read(ml, wl);
            assertEquals(1, wl.getMovies().size());
            assertEquals(0, ml.getMovies().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }



}
