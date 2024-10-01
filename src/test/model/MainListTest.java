package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainListTest {
    private MainList testMainList;
    private Movie testMovie;

    @BeforeEach
    void runBefore() {
        testMainList = new MainList();
        testMovie = new Movie("Inception", "Sci-Fi");
    }

    @Test
    void testAddMovie() {
        assertEquals("Movie added successfully!", testMainList.addMovie(testMovie));
        assertEquals(1, testMainList.getMovies().size());
        assertTrue(testMainList.getMovies().contains(testMovie));
    }
    @Test
    void testAddDuplicateMovie() {
        testMainList.addMovie(testMovie);
        assertEquals("Error: Movie is already present in the list!", testMainList.addMovie(testMovie));
        assertEquals(1, testMainList.getMovies().size());
    }

    @Test
    void testAddNonEqualMovie() {
        testMainList.addMovie(testMovie);
        Movie anotherMovie = new Movie("Movie", "Sci-Fi");
        assertEquals("Movie added successfully!", testMainList.addMovie(anotherMovie));
        assertEquals(2, testMainList.getMovies().size());
    }

    @Test
    void testClearMovies() {
        testMainList.clearMovies();
        assertEquals(0,testMainList.getMovies().size());
    }

}
