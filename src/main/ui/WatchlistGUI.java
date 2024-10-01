package ui;

import model.Movie;
import model.Watchlist;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WatchlistGUI {
    private WatchlistApp app;
    private JFrame frame;
    private JPanel moviePanel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem loadItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JButton addMovieButton;
    private JButton removeMovieButton;


    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Font HEADING_FONT = new Font("Arial", Font.BOLD, 20);


    // REQUIRES: An instance of WatchlistApp
    // EFFECTS: Initializes the GUI for the Watchlist application
    public WatchlistGUI(WatchlistApp app) {
        this.app = app;
        setDefaultUIFont(new javax.swing.plaf.FontUIResource(DEFAULT_FONT));
        showSplashScreen(this::initializeGraphics);
    }

    // EFFECTS: Sets the default UI font for all components
    private static void setDefaultUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Initializes the main frame and its components
    private void initializeGraphics() {
        frame = new JFrame("Watchlist Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        createMenuBar();

        // Create and set up the movie panel
        moviePanel = new JPanel();
        updateMoviePanel();
        frame.add(new JScrollPane(moviePanel), BorderLayout.CENTER);

        createButtons();


        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates and sets up the menu bar
    private void createMenuBar() {
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        menu.add(loadItem);
        menu.add(saveItem);
        menu.add(new JSeparator());
        menu.add(exitItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        loadItem.addActionListener(e -> loadWatchlist());
        saveItem.addActionListener(e -> saveWatchlist());
        exitItem.addActionListener(e -> System.exit(0));
    }

    // MODIFIES: this
    // EFFECTS: Creates and sets up buttons
    private void createButtons() {
        JPanel buttonPanel = new JPanel();
        JButton suggestMovieButton = new JButton("Suggest Movie");
        addMovieButton = new JButton("Add Movie to Watchlist");
        removeMovieButton = new JButton("Remove Movie from Watchlist");

        // Ensure these listeners are not creating new instances of WatchlistGUI
        suggestMovieButton.addActionListener(e -> addMovie());
        addMovieButton.addActionListener(e -> addMovieToWatchlist());
        removeMovieButton.addActionListener(e -> removeMovie());

        buttonPanel.add(suggestMovieButton);
        buttonPanel.add(addMovieButton);
        buttonPanel.add(removeMovieButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: Adds a selected movie to the watchlist
    private void addMovieToWatchlist() {
        // Getting movies from the main list
        List<Movie> availableMovies = app.getMainList().getMovies();
        String[] movieTitles = new String[availableMovies.size()];

        for (int i = 0; i < availableMovies.size(); i++) {
            movieTitles[i] = availableMovies.get(i).getTitle();
        }

        String selectedTitle = (String) JOptionPane.showInputDialog(
                frame,
                "Select a movie to add to your watchlist:",
                "Add Movie to Watchlist",
                JOptionPane.QUESTION_MESSAGE,
                null,
                movieTitles,
                movieTitles[0]
        );
        addAgainMovie(availableMovies, selectedTitle);
    }


    // REQUIRES: movie is already in the watchlist
    // EFFECTS: asks the user if they want to add movie to the watchlist again, adds the movie again
    private void addAgainMovie(List<Movie> availableMovies, String selectedTitle) {
        if (selectedTitle != null) {
            Movie selectedMovie = null;
            for (Movie movie : availableMovies) {
                if (movie.getTitle().equals(selectedTitle)) {
                    selectedMovie = movie;
                    break;
                }
            }

            addMovieAgainHelper(selectedTitle, selectedMovie);
        }
    }


    // REQUIRES: movie is already in the watchlist
    // EFFECTS: asks the user if they want to add movie to the watchlist again
    private void addMovieAgainHelper(String selectedTitle, Movie selectedMovie) {
        if (selectedMovie != null) {
            // Check if the movie is already in the watchlist
            if (app.getWatchlist().getMovies().contains(selectedMovie)) {
                int option = JOptionPane.showConfirmDialog(
                        frame,
                        selectedTitle + " is already in your watchlist. Do you want to add it again?",
                        "Confirm Add",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (option == JOptionPane.YES_OPTION) {
                    Movie duplicateMovie = new Movie(selectedMovie.getTitle(), selectedMovie.getGenre());
                    app.getWatchlist().addMovie(duplicateMovie);
                    updateMoviePanel();
                    JOptionPane.showMessageDialog(frame, selectedTitle + " added to your watchlist again.");
                }
            } else {
                // Movie not in watchlist, so add it
                app.getWatchlist().addMovie(selectedMovie);
                updateMoviePanel(); // Update the panel
                JOptionPane.showMessageDialog(frame, selectedTitle + " added to your watchlist.");
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: Updates the movie panel with current movie data
    private void updateMoviePanel() {
        moviePanel.removeAll();
        moviePanel.setLayout(new BorderLayout()); // Change to BorderLayout

        // Create and add the banner at the top
        ImageIcon bannerIcon = new ImageIcon("./data/banner.png");
        JLabel bannerLabel = new JLabel(bannerIcon);
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.add(bannerLabel, BorderLayout.CENTER);
        moviePanel.add(bannerPanel, BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        JPanel availableMoviesPanel = createAvailableMoviesPanel();
        JPanel watchlistPanel = createWatchlistPanel();
        contentPanel.add(availableMoviesPanel);
        contentPanel.add(watchlistPanel);
        moviePanel.add(contentPanel, BorderLayout.CENTER);

        moviePanel.revalidate();
        moviePanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: Creates a panel displaying available movies
    private JPanel createAvailableMoviesPanel() {
        // Main list heading
        JLabel mainListLabel = new JLabel("Available Movies:");
        mainListLabel.setFont(HEADING_FONT);
        JPanel mainListPanel = new JPanel();
        mainListPanel.setLayout(new BoxLayout(mainListPanel, BoxLayout.Y_AXIS));
        mainListPanel.add(mainListLabel);

        // Add movies to main list panel
        for (Movie movie : app.getMainList().getMovies()) {
            mainListPanel.add(new JLabel(movie.getTitle() + " - " + movie.getGenre()));
        }

        return mainListPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a panel displaying movies in the watchlist
    private JPanel createWatchlistPanel() {
        // Watchlist heading
        JLabel watchlistLabel = new JLabel("My Watchlist:");
        watchlistLabel.setFont(HEADING_FONT);
        JPanel watchlistPanel = new JPanel();
        watchlistPanel.setLayout(new BoxLayout(watchlistPanel, BoxLayout.Y_AXIS));
        watchlistPanel.add(watchlistLabel);

        // Add movies to watchlist panel
        for (Movie movie : app.getWatchlist().getMovies()) {
            watchlistPanel.add(new JLabel(movie.getTitle() + " - " + movie.getGenre()));
        }

        return watchlistPanel;
    }

    // MODIFIES: this, app
    // EFFECTS: Loads the watchlist from json file
    private void loadWatchlist() {
        String result = app.loadList();
        if ("Success".equals(result)) {
            updateMoviePanel();
        } else {
            JOptionPane.showMessageDialog(frame, "Error loading data.");
        }
    }

    // MODIFIES: this, app
    // EFFECTS: Saves the watchlist to a file
    private void saveWatchlist() {
        String result = app.saveList();
        if ("Success".equals(result)) {
            JOptionPane.showMessageDialog(frame, "Data saved successfully.");
        } else {
            JOptionPane.showMessageDialog(frame, "Error saving data.");
        }
    }

    // MODIFIES: this, app
    // EFFECTS: Adds a new movie to the list of available movies
    private void addMovie() {
        JTextField titleField = new JTextField(10);
        JTextField genreField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(new JLabel("Genre:"));
        panel.add(genreField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Enter details of the movie to add", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Movie newMovie = new Movie(titleField.getText(), genreField.getText());
            app.getMainList().addMovie(newMovie);
            updateMoviePanel();
        }
    }

    // MODIFIES: this, app
    // EFFECTS: Removes a selected movie from the watchlist
    private void removeMovie() {
        Watchlist watchlist = app.getWatchlist();
        String[] movieTitles = watchlist.getMovies().stream().map(Movie::getTitle).toArray(String[]::new);

        String selectedTitle = (String) JOptionPane.showInputDialog(null,
                "Select a movie to remove:",
                "Remove Movie",
                JOptionPane.QUESTION_MESSAGE,
                null,
                movieTitles,
                movieTitles[0]);

        if (selectedTitle != null) {
            watchlist.removeMovie(watchlist.getMovieByTitle(selectedTitle));
            updateMoviePanel();
        }
    }

//    private void showSplashScreen() {
//        JWindow splashScreen = new JWindow();
//        ImageIcon imageIcon = new ImageIcon("./data/start.png"); // Path to your splash image
//        Image image = imageIcon.getImage();
//        Image resizedImage = image.getScaledInstance(500, 500, Image.SCALE_SMOOTH); // Resize if necessary
//        imageIcon = new ImageIcon(resizedImage);
//
//        JLabel label = new JLabel(imageIcon);
//        splashScreen.getContentPane().add(label);
//        splashScreen.pack();
//        splashScreen.setLocationRelativeTo(null); // Center on screen
//
//        // Set a timer to close the splash screen and then initialize the main GUI
//        new Timer(3500, e -> {
//            splashScreen.dispose();
//        }).start();
//        splashScreen.setVisible(true);
//    }

    // MODIFIES: this
    // EFFECTS: Displays a splash screen and initializes the GUI after closing the splash screen
    private void showSplashScreen(Runnable onClosed) {
        JWindow splashScreen = new JWindow();
        ImageIcon imageIcon = new ImageIcon("./data/start.png");
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);

        JLabel label = new JLabel(imageIcon);
        splashScreen.getContentPane().add(label);
        splashScreen.pack();
        splashScreen.setLocationRelativeTo(null);
        Timer timer = new Timer(3500, e -> {
            splashScreen.dispose();
        });
        timer.setRepeats(false);
        timer.start();

        splashScreen.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (onClosed != null) {
                    onClosed.run();
                }
            }
        });

        splashScreen.setVisible(true);
    }
}
