package ui;

import model.MainList;
import model.Movie;
import model.Watchlist;

import java.util.InputMismatchException;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Represents the application, managing the user interface and interactions.
 * The application allows users to view, add, and remove movies from a watchlist,
 * utilizing data from the main list of available movies.
 */

public class WatchlistApp {
    private MainList mainList;
    private Watchlist watchlist;
    private Scanner input;
    private static final String JSON_STORE = "./data/watchlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: run the watchlist application
    public WatchlistApp() {
        mainList = new MainList();
        watchlist = new Watchlist("My Watchlist");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        //runWatchlist();
    }

    // REQUIRES: user input to be from the options in the menu
    // MODIFIES: this
    // EFFECTS: processes user input, calling other methods based on the user's choice until the user decides to quit.
//    public void runWatchlist() {
//        boolean keepGoing = true;
//        String input;
//
//        if (!loadList()) {  // try to load data from file
//            initializeMainList();  // if not loaded, initialize with default values
//        }
//        initializeWatchlist();   // always initialize watchlist
//
//        while (keepGoing) {
//            displayMenu();
//            input = this.input.next();
//            if (input.equalsIgnoreCase("q")) {
//                System.out.println(
//                        "Do you wish to save the watchlist and main list to file? (Enter 'Yes' or 'y' to confirm.)");
//                String i = this.input.next();
//                if (i.equalsIgnoreCase("y") || i.equalsIgnoreCase("yes")) {
//                    saveList();
//                } else {
//                    keepGoing = false;
//                }
//            } else {
//                processInput(input);
//            }
//        }
//        System.out.println("\nThank you for using the app! Goodbye!");
//    }


    // EFFECTS: initializes the main list with default movies
    public void initializeMainList() {
        mainList.addMovie(new Movie("Inception", "Sci-Fi"));
        mainList.addMovie(new Movie("Top Gun", "Action"));
        mainList.addMovie(new Movie("Batman", "Action"));
        mainList.addMovie(new Movie("Titanic", "Romance"));
    }

    // EFFECTS: initializes the watchlist and scanner
    public void initializeWatchlist() {
        watchlist = new Watchlist("My Watchlist");
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    public void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("v : view watchlist");
        System.out.println("a : add a movie to watchlist");
        System.out.println("r : remove a movie from watchlist");
        System.out.println("m : add a movie to the main list");
        System.out.println("s : save watchlist and main list to file");
        System.out.println("l : load watchlist and main list from file");
        System.out.println("q : quit");
    }

    // REQUIRES: Non-null String command from the menu.
    // MODIFIES: this
    // EFFECTS: processes user input and displays an error message if the input is wrong

    public void processInput(String input) {
        try {
            if (input.equalsIgnoreCase("v")) {
                viewWatchlist();
            } else if (input.equalsIgnoreCase("a")) {
                addMovieToWatchlist();
            } else if (input.equalsIgnoreCase("r")) {
                removeMovieFromWatchlist();
            } else if (input.equalsIgnoreCase("m")) {
                addMovieToMainList();
            } else if (input.equalsIgnoreCase("s")) {
                saveList();
            } else if (input.equalsIgnoreCase("l")) {
                loadList();
            } else {
                System.out.println("Wrong Input try again");
            }
        } catch (InputMismatchException e) {
            System.out.println("Wrong Input try again");
            this.input.nextLine();
        }
    }


    // EFFECTS: displays the watchlist with the movies added along with their genre
    public void viewWatchlist() {
        System.out.println("\n" + watchlist.getName() + ":");
        for (Movie movie : watchlist.getMovies()) {
            System.out.println(movie.getTitle() + ", " + movie.getGenre());
        }
    }

    // REQUIRES: user to choose movies from the mainList.getMovies()
    // MODIFIES: this
    // EFFECTS: adds a movie to the watchlist if it is not already present
    public void addMovieToWatchlist() {
        System.out.println("\nAvailable movies:");
        for (int i = 0; i < mainList.getMovies().size(); i++) {
            System.out.println((i + 1) + " -> " + mainList.getMovies().get(i).getTitle());
        }
        System.out.println("Select a movie to add:");
        int selection = input.nextInt();
        if (selection > 0 && selection <= mainList.getMovies().size()) {
            Movie selectedMovie = mainList.getMovies().get(selection - 1);
            if (isMovieInWatchlist(selectedMovie)) {
                System.out.println("The movie is already in the watchlist!");
            } else {
                watchlist.addMovie(selectedMovie);
                System.out.println("Movie added to the watchlist!");
            }
        } else {
            System.out.println("Invalid selection. Please try again.");
        }
    }


    // REQUIRES: Non-null Movie object.
    // EFFECTS: returns true if the movie is in the watchlist, false otherwise
    public boolean isMovieInWatchlist(Movie movie) {
        for (Movie m : watchlist.getMovies()) {
            if (m.getTitle().equals(movie.getTitle())) {
                return true;
            }
        }
        return false;
    }


    // MODIFIES: this
    // EFFECTS: removes a movie from the watchlist
    public void removeMovieFromWatchlist() {
        System.out.println("\n" + watchlist.getName() + ":");
        for (int i = 0; i < watchlist.getMovies().size(); i++) {
            System.out.println((i + 1) + " -> " + watchlist.getMovies().get(i).getTitle());
        }
        System.out.println("Select a movie to remove:");
        int selection = input.nextInt();
        if (selection > 0 && selection <= watchlist.getMovies().size()) {
            watchlist.removeMovie(watchlist.getMovies().get(selection - 1));
            System.out.println("Movie removed from the watchlist!");
        } else {
            System.out.println("Invalid selection. Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a movie to the mainlist
    private void addMovieToMainList() {
        System.out.println("\nAdd a movie to the main list.");
        System.out.println("Enter the title of the movie:");
        String title = input.next();
        System.out.println("Enter the genre of the movie:");
        String genre = input.next();
        Movie newMovie = new Movie(title, genre);
        String result = mainList.addMovie(newMovie);
        System.out.println(result);
    }


//    // EFFECTS: saves both the MainList and Watchlist to file
//    public void saveList() {
//        try {
//            jsonWriter.open();
//            jsonWriter.writeDatabase(mainList, watchlist); // save both MainList and Watchlist
//            jsonWriter.close();
//            System.out.println("Saved watchlist and main list to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads watchlist from file, returns true if loaded successfully, false otherwise
//    public boolean loadList() {
//        try {
//            jsonReader.read(mainList, watchlist);
//            System.out.println("Loaded watchlist and main list from " + JSON_STORE);
//            return true;
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//            return false;
//        }
//    }

    public String loadList() {
        try {
            jsonReader.read(mainList, watchlist);
            System.out.println("Loaded watchlist and main list from " + JSON_STORE);
            return "Success";
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            return "Fail";
        }
    }

    public String saveList() {
        try {
            jsonWriter.open();
            jsonWriter.writeDatabase(mainList, watchlist);
            jsonWriter.close();
            System.out.println("Saved watchlist and main list to " + JSON_STORE);
            return "Success";
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            return "Fail";
        }
    }


    public Watchlist getWatchlist() {
        return watchlist;
    }

    // Returns the current main list
    public MainList getMainList() {
        return mainList;
    }

    // Searches for a movie by title in the watchlist
    public Movie getMovieByTitle(String title) {
        for (Movie movie : watchlist.getMovies()) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null; // Return null if movie is not found
    }

}
