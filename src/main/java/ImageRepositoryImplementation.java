import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ImageRepositoryImplementation implements ImageRepository {

    private static final String INSERT_INTO_IMAGES = "INSERT INTO images (name, time, key, size) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_ORDER_BY_BY_SIZE_DESC = "SELECT * FROM images ORDER BY size DESC";
    private static final String SELECT_BY_SIZE_RANGE = "SELECT * FROM images WHERE size BETWEEN ";
    private final DataSource dataSource;

    public ImageRepositoryImplementation(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Image image) {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(INSERT_INTO_IMAGES)) {
            preparedStatement.setString(1, image.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(image.getCreatingTimestamp()));
            preparedStatement.setString(3, image.getS3ObjectKey());
            preparedStatement.setInt(4, image.getSize());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ImageDisplayDto> getGlobalTop() {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SELECT_ALL_ORDER_BY_BY_SIZE_DESC)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getImageDisplayDtos(resultSet);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ImageDisplayDto> getTopBySizeRange(int min, int max) {
        try (final Connection connection = dataSource.getConnection()) {
            String query = SELECT_BY_SIZE_RANGE + min*1000 + " AND " + max*1000;
            query += " ORDER BY size DESC";
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            return getImageDisplayDtos(resultSet);
        } catch (SQLException e) {
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
