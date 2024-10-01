package persistence;

import model.Movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class is adapted from the JsonTest class of JsonSerializationDemo.
 */
public class JsonTest {
    protected void checkMovie(String name, String genre, Movie movie) {
        assertEquals(name, movie.getTitle());
        assertEquals(genre, movie.getGenre());
    }

}
