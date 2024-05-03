package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

public class MovieRepository {

    private Dao<MovieEntity, Integer> movieDao;

    public MovieRepository(Dao<MovieEntity, Integer> movieDao) {
        this.movieDao = movieDao;
    }

    // Method to add a movie to the database
    public void addMovie(MovieEntity movie) {
        try {
            movieDao.create(movie);
        } catch (SQLException e) {
            System.err.println("Error adding movie: " + e.getMessage());
        }
    }

    // Method to update a movie in the database
    public void updateMovie(MovieEntity movie) {
        try {
            movieDao.update(movie);
        } catch (SQLException e) {
            System.err.println("Error updating movie: " + e.getMessage());
        }
    }

    // Method to delete a movie from the database
    public void deleteMovie(MovieEntity movie) {
        try {
            movieDao.delete(movie);
        } catch (SQLException e) {
            System.err.println("Error deleting movie: " + e.getMessage());
        }
    }

    // Method to find a movie by its ID
    public MovieEntity findById(int id) {
        try {
            return movieDao.queryForId(id);
        } catch (SQLException e) {
            System.err.println("Error finding movie: " + e.getMessage());
            return null;
        }
    }

    // Method to retrieve all movies from the database
    public List<MovieEntity> findAllMovies() {
        try {
            return movieDao.queryForAll();
        } catch (SQLException e) {
            System.err.println("Error retrieving all movies: " + e.getMessage());
            return null;
        }
    }
}

