import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// TODO: GET RID of BD in naming because Repository pattern used
public class ImageRepositoryDB implements ImageRepository {

    // TODO: reorder modifiers (static final)
    private final static String INSERT_INTO_IMAGES = "INSERT INTO images (name, time, key, size) VALUES (?, ?, ?, ?)";
    private final static String SELECT_ALL_ORDER_BY_BY_SIZE_DESC = "SELECT * FROM images ORDER BY size DESC"; // TODO: BY_BY?
    private final static String SELECT_BY_SIZE_RANGE = "SELECT * FROM images WHERE size BETWEEN ";
    private final DataSource dataSource;

    public ImageRepositoryDB(DataSource dataSource) {
        this.dataSource = dataSource;
        // TODO: create datasource connection only once - dataSource.getConnection()
        // TODO: read code of getConnection() method in BaseDataSource
    }

    @Override
    public void save(Image img) {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(INSERT_INTO_IMAGES)) {
            preparedStatement.setString(1, img.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(img.getTimeOfCreating()));
            preparedStatement.setString(3, img.getKey());
            preparedStatement.setInt(4, img.getSize());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO: add logging
            // TODO: DON'T write e.printStackTrace()!! Create custom exception with message and rethrow this
            e.printStackTrace();
        }
    }

    @Override
    public List<ImageDisplayDto> getGlobalTop() {
        // TODO: what about image duplicates?
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SELECT_ALL_ORDER_BY_BY_SIZE_DESC)) {
            ResultSet resultSet = preparedStatement.executeQuery(); // TODO: inline this resultSet
            return getImageDisplayDtos(resultSet);
        } catch (SQLException e) {
            // TODO: add logging
            // TODO: do you handle empty ArrayList above?
            return new ArrayList<>();
        }
    }

    // TODO: rename min/max to from/to
    // TODO: rewrite this method WITHOUT concatenation
    @Override
    public List<ImageDisplayDto> getTopBySizeRange(int min, int max) {
        try (final Connection connection = dataSource.getConnection()) {

            // TODO: keep Java conventions - min * 1000, max * 1000
            // TODO: don't use concatenation in SQL queries. This provides SQL injection
            String query = SELECT_BY_SIZE_RANGE + min*1000 + " AND " + max*1000;
            query += " ORDER BY size DESC"; // TODO: don't use concatenation in SQL queries. This provides SQL injection

            ResultSet resultSet = connection.prepareStatement(query).executeQuery(); // TODO: inline this resultSet
            return getImageDisplayDtos(resultSet);
        } catch (SQLException e) {
            // TODO: add logging
            // TODO: do you handle empty ArrayList above?
            return new ArrayList<>();
        }
    }

    private List<ImageDisplayDto> getImageDisplayDtos(ResultSet resultSet) throws SQLException {
        List<ImageDisplayDto> imageDisplayDTOList = new ArrayList<>();
        while (resultSet.next()) {
            ImageDisplayDto imageDisplayDTO = new ImageDisplayDto();
            imageDisplayDTO.setName(resultSet.getString("name"));
            imageDisplayDTO.setTime(resultSet.getTimestamp("time").toLocalDateTime());
            imageDisplayDTO.setKey(resultSet.getString("key"));
            imageDisplayDTO.setSize(resultSet.getInt("size"));
            imageDisplayDTOList.add(imageDisplayDTO);
        }
        return imageDisplayDTOList;
    }
}
