package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.dao.ForeignCollection;

@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "movie_id")
    private MovieEntity movie;

    // ORMLite requires a no-arg constructor
    public WatchlistMovieEntity() {
    }

    // Constructor for creating a new WatchlistMovieEntity
    public WatchlistMovieEntity(MovieEntity movie) {
        this.movie = movie;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "WatchlistMovieEntity{" +
                "id=" + id +
                ", movie=" + (movie != null ? movie.getTitle() : "null") +
                '}';
    }
}
