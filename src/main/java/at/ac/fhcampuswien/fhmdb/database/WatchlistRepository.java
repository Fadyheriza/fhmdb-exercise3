package at.ac.fhcampuswien.fhmdb.database;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.support.ConnectionSource;

public class WatchlistRepository {
    private final Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository(ConnectionSource connectionSource) throws SQLException {
        dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
    }

    public List<WatchlistMovieEntity> getWatchlist() throws SQLException {
        return dao.queryForAll();
    }

    public int addToWatchlist(WatchlistMovieEntity movie) throws SQLException {
        List<WatchlistMovieEntity> existing = dao.queryForEq("apiId", movie.getApiId());
        if (existing.isEmpty()) {
            return dao.create(movie);
        }
        return 0; // Or return existing.get(0).getId() if you want to return the ID of the existing entry.
    }

    public int removeFromWatchlist(String apiId) throws SQLException {
        List<WatchlistMovieEntity> movies = dao.queryForEq("apiId", apiId);
        return dao.delete(movies);
    }
}
