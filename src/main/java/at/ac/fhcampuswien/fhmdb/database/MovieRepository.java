package at.ac.fhcampuswien.fhmdb.database;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.support.ConnectionSource;

public class MovieRepository {
    private final Dao<MovieEntity, Long> dao;

    public MovieRepository(ConnectionSource connectionSource) throws SQLException {
        dao = DaoManager.createDao(connectionSource, MovieEntity.class);
    }

    public List<MovieEntity> getAllMovies() throws SQLException {
        return dao.queryForAll();
    }

    public int removeAII() throws SQLException {
        return dao.deleteBuilder().delete();
    }

    public MovieEntity getMovie(Long id) throws SQLException {
        return dao.queryForId(id);
    }

    public int addAllMovies(List<MovieEntity> movies) throws SQLException {
        return dao.create(movies);
    }
}
