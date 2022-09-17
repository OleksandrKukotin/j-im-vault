import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLImageRepository implements ImageRepository {

    private static final String INSERT_INTO_IMAGES = "INSERT INTO images (name, time, key, size) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_ORDER_BY_SIZE_DESC = "SELECT * FROM images ORDER BY size DESC";
    private static final String SELECT_BY_SIZE_RANGE = "SELECT * FROM images WHERE size BETWEEN ? AND ? ORDER BY size DESC";
    private static final String ERROR_MESSAGE = "An error occurred during executing query or setting up connection";
    private static final int MULTIPLIER_FROM_KB_TO_MB = 1000;

    private static final Logger logger = Logger.getLogger(PostgreSQLImageRepository.class);

    private final Connection connection;

    public PostgreSQLImageRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBConnectionException("Cannot connect to database", e);
        }
    }

    @Override
    public void save(Image image) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_IMAGES)) {
            preparedStatement.setString(1, image.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(image.getCreatingTimestamp()));
            preparedStatement.setString(3, image.getImageKeyOnS3());
            preparedStatement.setInt(4, image.getSize());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE);
        }
    }

    @Override
    public List<Image> findAll() {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDER_BY_SIZE_DESC)) {
            return getImages(preparedStatement.executeQuery());
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Image> findImagesInSizeRange(int from, int to) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_SIZE_RANGE)) {
            preparedStatement.setInt(1, from * MULTIPLIER_FROM_KB_TO_MB);
            preparedStatement.setInt(2, to * MULTIPLIER_FROM_KB_TO_MB);
            return getImages(preparedStatement.executeQuery());
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
            return new ArrayList<>();
        }
    }

    private List<Image> getImages(ResultSet resultSet) throws SQLException {
        List<Image> imagesList = new ArrayList<>();
        while (resultSet.next()) {
            imagesList.add(new Image(
                resultSet.getString("name"),
                resultSet.getTimestamp("time").toLocalDateTime(),
                resultSet.getString("key"),
                resultSet.getInt("size"))
            );
        }
        return imagesList;
    }

    private static final class DBConnectionException extends RuntimeException {

        DBConnectionException(String message, Exception exception) {
            super(message, exception);
        }
    }
}
