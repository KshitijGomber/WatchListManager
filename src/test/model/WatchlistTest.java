package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WatchlistTest {
    private Watchlist testWatchlist;
    private Movie testMovie;
    private Movie anotherMovie;

    @BeforeEach
    void runBefore() {
        testWatchlist = new Watchlist("Test watchlist");
        testMovie = new Movie("Inception", "Sci-Fi");
        anotherMovie = new Movie("Interstellar", "Sci-Fi");
    }

    @Test
    void testAddMovie() {
        assertEquals("Movie added to watchlist successfully!", testWatchlist.addMovie(testMovie));
        assertEquals(1, testWatchlist.getMovies().size());
        assertTrue(testWatchlist.getMovies().contains(testMovie));
    }

    @Test
    void testAddDuplicateMovie() {
        testWatchlist.addMovie(testMovie);
        assertEquals("Error: Movie is already in the watchlist!", testWatchlist.addMovie(testMovie));
        assertEquals(1, testWatchlist.getMovies().size());
    }

    @Test
    void testRemoveMovie() {
        testWatchlist.addMovie(testMovie);
        assertEquals("Movie removed from watchlist successfully!", testWatchlist.removeMovie(testMovie));
        assertEquals(0, testWatchlist.getMovies().size());
        assertFalse(testWatchlist.getMovies().contains(testMovie));
    }

    @Test
    void testRemoveNonExistentMovie() {
        assertEquals("Error: Movie is not in the watchlist!", testWatchlist.removeMovie(testMovie));
        assertFalse(testWatchlist.getMovies().contains(testMovie));
    }

    @Test
    void testName() {
        assertEquals("Test watchlist", testWatchlist.getName());
        assertNotEquals("Watchlist", testWatchlist.getName());
    }

    @Test
    void testAddNonEqualMovie() {
        testWatchlist.addMovie(testMovie);
        Movie anotherMovie = new Movie("Movie", "Sci-Fi");
        assertEquals("Movie added to watchlist successfully!", testWatchlist.addMovie(anotherMovie));
        assertEquals(2, testWatchlist.getMovies().size());
    }

    @Test
    void testRemoveNonEqualMovie() {
        testWatchlist.addMovie(testMovie);
        Movie anotherMovie = new Movie("Movie", "Sci-Fi");
        assertEquals("Error: Movie is not in the watchlist!", testWatchlist.removeMovie(anotherMovie));
        assertEquals(1, testWatchlist.getMovies().size());
    }

    @Test
    void testRemoveWhenMultipleMovies() {
        Movie anotherMovie = new Movie("Movie", "Sci-Fi");
        testWatchlist.addMovie(testMovie);
        testWatchlist.addMovie(anotherMovie);
        assertEquals("Movie removed from watchlist successfully!", testWatchlist.removeMovie(testMovie));
        assertEquals(1, testWatchlist.getMovies().size());
        assertFalse(testWatchlist.getMovies().contains(testMovie));
        assertTrue(testWatchlist.getMovies().contains(anotherMovie));
    }

    @Test
    void testGetMovieByTitleExisting() {
        testWatchlist.addMovie(testMovie);
        Movie foundMovie = testWatchlist.getMovieByTitle("Inception");
        assertNotNull(foundMovie, "Movie should be found");
        assertEquals(testMovie, foundMovie, "Found movie should match the expected movie");
    }

    @Test
    void testGetMovieByTitleCaseInsensitive() {
        testWatchlist.addMovie(testMovie);
        Movie foundMovie = testWatchlist.getMovieByTitle("inCePtIoN");
        assertNotNull(foundMovie, "Movie should be found regardless of case");
        assertEquals(testMovie, foundMovie, "Found movie should match the expected movie regardless of case");
    }

    @Test
    void testGetMovieByTitleNonExistent() {
        testWatchlist.addMovie(testMovie);
        Movie foundMovie = testWatchlist.getMovieByTitle("Non Existent Movie");
        assertNull(foundMovie, "Non-existent movie should return null");
    }

    @Test
    void testGetMovieByTitleEmptyWatchlist() {
        Movie foundMovie = testWatchlist.getMovieByTitle("Any Movie");
        assertNull(foundMovie, "Empty watchlist should not find any movie");
    }

    @Test
    void testGetMovieByTitleMultipleMovies() {
        testWatchlist.addMovie(testMovie);
        testWatchlist.addMovie(anotherMovie);
        Movie foundMovie = testWatchlist.getMovieByTitle("Interstellar");
        assertNotNull(foundMovie, "Movie should be found in a watchlist with multiple movies");
        assertEquals(anotherMovie, foundMovie, "Found movie should match the expected movie in a watchlist with multiple movies");
    }
}
