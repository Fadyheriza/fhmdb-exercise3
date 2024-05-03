package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {

    private Dao<WatchlistMovieEntity, Integer> watchlistDao;

    public WatchlistRepository(Dao<WatchlistMovieEntity, Integer> watchlistDao) {
        this.watchlistDao = watchlistDao;
    }

    public static MovieCell getInstance() {
        return new MovieCell();
    }

    // Method to add a movie to the watchlist
    public void addToWatchlist(WatchlistMovieEntity watchlistMovie) {
        try {
            watchlistDao.create(watchlistMovie);
        } catch (SQLException e) {
            System.err.println("Error adding movie to watchlist: " + e.getMessage());
        }
    }

    // Method to remove a movie from the watchlist
    public void removeFromWatchlist(WatchlistMovieEntity watchlistMovie) {
        try {
            watchlistDao.delete(watchlistMovie);
        } catch (SQLException e) {
            System.err.println("Error removing movie from watchlist: " + e.getMessage());
        }
    }

    // Method to find a watchlist entry by its ID
    public WatchlistMovieEntity findById(int id) {
        try {
            return watchlistDao.queryForId(id);
        } catch (SQLException e) {
            System.err.println("Error finding watchlist entry: " + e.getMessage());
            return null;
        }
    }

    // Method to retrieve all entries from the watchlist
    public List<WatchlistMovieEntity> findAllEntries() {
        try {
            return watchlistDao.queryForAll();
        } catch (SQLException e) {
            System.err.println("Error retrieving all watchlist entries: " + e.getMessage());
            return null;
        }
    }
}
