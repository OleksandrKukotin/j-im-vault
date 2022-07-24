import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// TODO: Rename to ImageRepositoryImpl - but is is also bad naming pattern
public class ImageRepositoryImplementation implements ImageRepository {

    private static final String INSERT_INTO_IMAGES = "INSERT INTO images (name, time, key, size) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_ORDER_BY_BY_SIZE_DESC = "SELECT * FROM images ORDER BY size DESC"; // TODO: you repeated the mistake - check previous code review and fix this!!!!
    private static final String SELECT_BY_SIZE_RANGE = "SELECT * FROM images WHERE size BETWEEN ? AND ? ORDER BY size DESC";
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
            // TODO: you repeated the mistake - DONT WRITE "e.printStackTrace()" - check previous code review and fix this!!!!
            // TODO: add logging
            // TODO: DON'T write e.printStackTrace()!! Create custom exception with message and rethrow this
            e.printStackTrace();
        }
    }

    @Override
    public List<ImageDto> getGlobalTop() {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SELECT_ALL_ORDER_BY_BY_SIZE_DESC)) {
            ResultSet resultSet = preparedStatement.executeQuery(); // TODO: INLINE THIS!
            return getImageDtos(resultSet);
        } catch (SQLException e) {
            // TODO: you repeated the mistake - check previous code review and fix this!!!!
            // TODO: add logging
            // TODO: do you handle empty ArrayList above?
            return new ArrayList<>();
        }
    }

    @Override
    public List<ImageDto> getTopBySizeRange(int from, int to) {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SELECT_BY_SIZE_RANGE)) {
            preparedStatement.setInt(1, from*1000); // TODO: USE SPACES - from * 1000
            preparedStatement.setInt(2, to*1000); // TODO: USE SPACES - to * 1000
            ResultSet resultSet = preparedStatement.executeQuery(); // TODO: INLINE THIS!
            return getImageDtos(resultSet);
        } catch (SQLException e) {
            // TODO: you repeated the mistake - check previous code review and fix this!!!!
            // TODO: add logging
            // TODO: do you handle empty ArrayList above?
            return new ArrayList<>();
        }
    }

    // TODO: hande SQLException in this method, use Image instead ImageDto (for details see comment in ImageDto class)
    private List<ImageDto> getImageDtos(ResultSet resultSet) throws SQLException {
        List<ImageDto> imageDTOList = new ArrayList<>();
        while (resultSet.next()) {
            imageDTOList.add(new ImageDto(
                resultSet.getTimestamp("time").toLocalDateTime(),
                resultSet.getString("name"),
                resultSet.getString("key"),
                resultSet.getInt("size")
            ));
        }
        return imageDTOList;
    }
}
