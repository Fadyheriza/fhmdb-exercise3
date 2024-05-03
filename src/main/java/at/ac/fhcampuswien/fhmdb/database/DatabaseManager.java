package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;


public class DatabaseManager {
    // This JDBC URL specifies an in-memory H2 database named `fhmdb`
    private static final String DATABASE_URL = "jdbc:h2:mem:fhmdb;DB_CLOSE_DELAY=-1"; // Keep the database in memory until the VM exits

    private ConnectionSource connectionSource;

    public DatabaseManager() {
        try {
            // Establish the connection to the database
            connectionSource = new JdbcConnectionSource(DATABASE_URL);

            // Create tables if they do not exist
            TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException e) {
            System.err.println("Unable to create database connection or tables: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Get DAO for MovieEntity
    public Dao<MovieEntity, Integer> getMovieDao() throws SQLException {
        return DaoManager.createDao(connectionSource, MovieEntity.class);
    }

    // Get DAO for WatchlistMovieEntity
    public Dao<WatchlistMovieEntity, Integer> getWatchlistDao() throws SQLException {
        return DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
    }

    public void closeConnection() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (IOException e) {
                System.err.println("Failed to close the database connection due to an IO error: " + e.getMessage());
            }
        }
    }


    @Override
    protected void finalize() throws Throwable {
        closeConnection(); // Ensure the connection is closed when the object is garbage collected
        super.finalize();
    }
}
