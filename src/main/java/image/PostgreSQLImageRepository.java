package image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLImageRepository implements ImageRepository {

    private static final String INSERT_INTO_IMAGES = "INSERT INTO images (name, time, key, size) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_ORDER_BY_SIZE_DESC = "SELECT * FROM images ORDER BY size DESC";
    private static final String SELECT_BY_SIZE_RANGE = "SELECT * FROM images WHERE size BETWEEN ? AND ? ORDER BY size DESC";
    private static final String ERROR_MESSAGE = "An error occurred during executing query or setting up connection";
    private static final Logger LOGGER = LoggerFactory.getLogger(PostgreSQLImageRepository.class);
    private static final int FROM_KB_TO_MB_MULTIPLIER = 1000;

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
            preparedStatement.setString(1, image.name());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(image.creatingTimestamp()));
            preparedStatement.setString(3, image.imageKeyOnS3());
            preparedStatement.setInt(4, image.size());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(ERROR_MESSAGE);
        }
    }

    @Override
    public List<Image> findAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDER_BY_SIZE_DESC)) {
            return getImages(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error(ERROR_MESSAGE, e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Image> findImagesInSizeRange(int from, int to) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_SIZE_RANGE)) {
            preparedStatement.setInt(1, from * FROM_KB_TO_MB_MULTIPLIER);
            preparedStatement.setInt(2, to * FROM_KB_TO_MB_MULTIPLIER);
            return getImages(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error(ERROR_MESSAGE, e);
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

        private DBConnectionException(String message, Exception exception) {
            super(message, exception);
        }
    }
}
