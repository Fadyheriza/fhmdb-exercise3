package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final JFXButton detailBtn = new JFXButton("Show Details");
    private final JFXButton addToWatchlistBtn = new JFXButton("Add to Watchlist");
    private final JFXButton removeFromWatchlistBtn = new JFXButton("Remove from Watchlist");
    private final VBox layout = new VBox();
    private final HBox buttons = new HBox();
    private final VBox detailsBox = new VBox();
    private boolean detailsVisible = false;

    @FunctionalInterface
    public interface ClickEventHandler<T> {
        void onClick(T t);
    }

    public MovieCell(ClickEventHandler<Movie> addToWatchlistClicked, ClickEventHandler<Movie> removeFromWatchlistClicked) {
        super();
        configureButtons(addToWatchlistClicked, removeFromWatchlistClicked);
        setupLayout();
        setupButtonActions();
    }

    private void configureButtons(ClickEventHandler<Movie> addToWatchlistClicked, ClickEventHandler<Movie> removeFromWatchlistClicked) {
        addToWatchlistBtn.setStyle("-fx-background-color: #76ff03;");  // Green button
        removeFromWatchlistBtn.setStyle("-fx-background-color: #ff1744;");  // Red button
        detailBtn.setStyle("-fx-background-color: #f5c518;");  // Yellow button

        addToWatchlistBtn.setOnAction(event -> addToWatchlistClicked.onClick(getItem()));
        removeFromWatchlistBtn.setOnAction(event -> removeFromWatchlistClicked.onClick(getItem()));
    }

    private void setupLayout() {
        title.getStyleClass().add("text-yellow");
        detail.getStyleClass().add("text-white");
        genre.getStyleClass().add("text-white");
        genre.setStyle("-fx-font-style: italic");
        title.setFont(Font.font(20));
        detail.setWrapText(true);

        buttons.getChildren().addAll(detailBtn, addToWatchlistBtn, removeFromWatchlistBtn);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER_LEFT);

        layout.getChildren().addAll(title, detail, genre, buttons, detailsBox);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));
    }

    private void setupButtonActions() {
        detailBtn.setOnAction(event -> toggleDetails());
    }

    private void toggleDetails() {
        if (!detailsVisible) {
            detailsBox.getChildren().setAll(getDetails());
            detailsVisible = true;
            detailBtn.setText("Hide Details");
        } else {
            detailsBox.getChildren().clear();
            detailsVisible = false;
            detailBtn.setText("Show Details");
        }
    }

    private VBox getDetails() {
        Label releaseYear = new Label("Release Year: " + getItem().getReleaseYear());
        Label length = new Label("Length: " + getItem().getLengthInMinutes() + " minutes");
        Label rating = new Label("Rating: " + getItem().getRating() + "/10");
        Label directors = new Label("Directors: " + String.join(", ", getItem().getDirectors()));
        Label writers = new Label("Writers: " + String.join(", ", getItem().getWriters()));
        Label mainCast = new Label("Main Cast: " + String.join(", ", getItem().getMainCast()));

        releaseYear.getStyleClass().add("text-white");
        length.getStyleClass().add("text-white");
        rating.getStyleClass().add("text-white");
        directors.getStyleClass().add("text-white");
        writers.getStyleClass().add("text-white");
        mainCast.getStyleClass().add("text-white");

        return new VBox(releaseYear, length, rating, directors, writers, mainCast);
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);
        setText(null);  // Important for custom cell appearance

        if (empty || movie == null) {
            setGraphic(null);
        } else {
            title.setText(movie.getTitle());
            detail.setText(movie.getDescription() != null ? movie.getDescription() : "No description available");
            genre.setText(movie.getGenres().stream().map(Enum::toString).collect(Collectors.joining(", ")));
            setGraphic(layout);
        }
    }
}
