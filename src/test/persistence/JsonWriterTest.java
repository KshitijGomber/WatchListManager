package persistence;

import model.MainList;
import model.Movie;
import model.Watchlist;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is adapted from the JsonWriterTest class of JsonSerializationDemo.
 */
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            MainList ml = new MainList();
            Watchlist wl = new Watchlist("Test");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDatabase() {
        try {
            MainList ml = new MainList();
            Watchlist wl = new Watchlist("Test");
            JsonWriter writer = new JsonWriter("./data/testEmptyDatabase.json");
            writer.open();
            writer.writeDatabase(ml, wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyDatabase.json");
            reader.read(ml, wl);
            assertEquals(0, ml.getMovies().size());
            assertEquals(0, wl.getMovies().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDatabase() {
        try {
            MainList ml = new MainList();
            ml.addMovie(new Movie("Inception", "Sci-Fi"));
            Watchlist wl = new Watchlist("Test");
            wl.addMovie(new Movie("RRR", "Action"));
            JsonWriter writer = new JsonWriter("./data/testGeneralDatabase.json");
            writer.open();
            writer.writeDatabase(ml, wl);
            writer.close();
            JsonReader reader = new JsonReader("./data/testGeneralDatabase.json");
            reader.read(ml, wl);
            assertEquals(1, ml.getMoviesSize());
            assertEquals(1, wl.getMovies().size());
            checkMovie("Inception", "Sci-Fi", ml.getMovies().get(0));
            checkMovie("RRR", "Action", wl.getMovies().get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
