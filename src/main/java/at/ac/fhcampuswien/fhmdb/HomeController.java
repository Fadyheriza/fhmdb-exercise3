package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeController implements Initializable {
    @FXML
    private JFXButton searchBtn, homeBtn, watchlistBtn, aboutBtn;

    @FXML
    private TextField searchField;

    @FXML
    private JFXListView<Movie> movieListView;

    @FXML
    private JFXComboBox<String> genreComboBox, releaseYearComboBox, ratingFromComboBox;

    @FXML
    private JFXButton sortBtn;

    private List<Movie> allMovies;
    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    private SortedState sortedState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
    }


    public void initializeState() {
        List<Movie> result = MovieAPI.getAllMovies();
        setMovies(result);
        setMovieList(result);
        sortedState = SortedState.NONE;
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.getItems().add("No filter");
        genreComboBox.getItems().addAll(Arrays.stream(Genre.values()).map(Enum::name).collect(Collectors.toList()));
        genreComboBox.setPromptText("Filter by Genre");

        releaseYearComboBox.getItems().add("No filter");
        releaseYearComboBox.getItems().addAll(IntStream.range(1900, 2024).mapToObj(String::valueOf).collect(Collectors.toList()));
        releaseYearComboBox.setPromptText("Filter by Release Year");

        ratingFromComboBox.getItems().add("No filter");
        ratingFromComboBox.getItems().addAll(IntStream.rangeClosed(0, 10).mapToObj(String::valueOf).collect(Collectors.toList()));
        ratingFromComboBox.setPromptText("Filter by Rating");
    }


    public void setMovies(List<Movie> movies) {
        allMovies = movies;
    }

    public void setMovieList(List<Movie> movies) {
        observableMovies.clear();
        observableMovies.addAll(movies);
    }

    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            sortMovies(SortedState.ASCENDING);
        } else if (sortedState == SortedState.ASCENDING) {
            sortMovies(SortedState.DESCENDING);
        }
    }

    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending
    public void sortMovies(SortedState sortDirection) {
        if (sortDirection == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }

    public List<Movie> filterByQuery(List<Movie> movies, String query) {
        if (query == null || query.isEmpty()) return movies;

        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream().filter(movie ->
                        movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                movie.getDescription().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public List<Movie> filterByGenre(List<Movie> movies, Genre genre) {
        if (genre == null) return movies;

        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream().filter(movie -> movie.getGenres().contains(genre)).toList();
    }

    public void applyAllFilters(String searchQuery, Object genre) {
        List<Movie> filteredMovies = allMovies;

        if (!searchQuery.isEmpty()) {
            filteredMovies = filterByQuery(filteredMovies, searchQuery);
        }

        if (genre != null && !genre.toString().equals("No filter")) {
            filteredMovies = filterByGenre(filteredMovies, Genre.valueOf(genre.toString()));
        }

        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }

    public void searchBtnClicked(ActionEvent actionEvent) {
        String searchQuery = searchField.getText().trim().toLowerCase();
        String releaseYear = validateComboboxValue(releaseYearComboBox.getSelectionModel().getSelectedItem());
        String ratingFrom = validateComboboxValue(ratingFromComboBox.getSelectionModel().getSelectedItem());
        String genreValue = validateComboboxValue(genreComboBox.getSelectionModel().getSelectedItem());

        Genre genre = null;
        if (genreValue != null) {
            genre = Genre.valueOf(genreValue);
        }

        List<Movie> movies = getMovies(searchQuery, genre, releaseYear, ratingFrom);
        setMovies(movies);
        setMovieList(movies);
        // applyAllFilters(searchQuery, genre);

        sortMovies(sortedState);
    }

    public String validateComboboxValue(Object value) {
        if (value != null && !value.toString().equals("No filter")) {
            return value.toString();
        }
        return null;
    }

    public List<Movie> getMovies(String searchQuery, Genre genre, String releaseYear, String ratingFrom) {
        return MovieAPI.getAllMovies(searchQuery, genre, releaseYear, ratingFrom);
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }

    // count which actor is in the most movies
    public String getMostPopularActor(List<Movie> movies) {
        String actor = movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

        return actor;
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .mapToInt(movie -> movie.getTitle().length())
                .max()
                .orElse(0);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirectors().contains(director))
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

    // Functionality to switch to Watchlist screen
    @FXML
    public void switchToWatchlist(ActionEvent actionEvent) {
        // Load the FXML file for the Watchlist screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home-view.fxml"));
        Parent watchlistRoot;
        try {
            watchlistRoot = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Get the scene from the current button
        Scene scene = searchBtn.getScene();

        // Replace the content of the current scene with the Watchlist screen
        scene.setRoot(watchlistRoot);
    }
}
