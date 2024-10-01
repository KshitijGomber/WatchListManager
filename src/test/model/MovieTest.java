package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {
    private Movie testMovie;

    @BeforeEach
    void runBefore() {
        testMovie = new Movie("Inception", "Sci-Fi");
    }

    @Test
    void testConstructor() {
        assertEquals("Inception", testMovie.getTitle());
        assertEquals("Sci-Fi", testMovie.getGenre());
    }

    @Test
    void testEquality() {
        Movie anotherMovie = new Movie("Inception", "Sci-Fi");
        assertEquals(testMovie.toString(), anotherMovie.toString());
    }

    @Test
    void testInequality() {
        Movie anotherMovie = new Movie("Interstellar", "Sci-Fi");
        assertNotEquals(testMovie, anotherMovie);
    }

    @Test
    void testToString() {
        assertEquals("Inception (Sci-Fi)", testMovie.toString());
    }
}
