package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
    private final JFXButton addToWatchlistBtn = new JFXButton("Add to Watchlist"); // Added button
    private final VBox layout = new VBox(title, detail, genre, detailBtn, addToWatchlistBtn); // Added button to layout
    private boolean collapsedDetails = true;

    public MovieCell() {
        super();

        // Set styles and layout
        detailBtn.setStyle("-fx-background-color: #f5c518;");
        addToWatchlistBtn.setStyle("-fx-background-color: #f5c518;"); // Added button style
        title.getStyleClass().add("text-yellow");
        detail.getStyleClass().add("text-white");
        genre.getStyleClass().add("text-white");
        genre.setStyle("-fx-font-style: italic");
        layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));
        title.setFont(Font.font(20));
        detail.setWrapText(true);
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER_LEFT);

        // Set action for "Show Details" button
        detailBtn.setOnAction(event -> {
            if (collapsedDetails) {
                layout.getChildren().add(getDetails());
                collapsedDetails = false;
                detailBtn.setText("Hide Details");
            } else {
                layout.getChildren().remove(4);
                collapsedDetails = true;
                detailBtn.setText("Show Details");
            }
            setGraphic(layout);
        });

        // Set action for "Add to Watchlist" button
        addToWatchlistBtn.setOnAction(event -> {
            Movie movie = getItem();
            if (movie != null) {
                addToWatchlist(movie);
            }
        });
    }

    private void addToWatchlist(Movie movie) {
        WatchlistRepository.getInstance().addToWatchlist(movie); // Adjusted method call
        // Provide visual feedback to the user
        addToWatchlistBtn.setText("Added");
        addToWatchlistBtn.setDisable(true); // Disable the button after adding the movie
    }

    private VBox getDetails() {
        VBox details = new VBox();
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

        details.getChildren().add(releaseYear);
        details.getChildren().add(rating);
        details.getChildren().add(length);
        details.getChildren().add(directors);
        details.getChildren().add(writers);
        details.getChildren().add(mainCast);
        return details;
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setGraphic(null);
            setText(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );

            String genres = movie.getGenres()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
            genre.setText(genres);

            detail.setMaxWidth(this.getScene().getWidth() - 30);

            setGraphic(layout);
        }
    }
}
