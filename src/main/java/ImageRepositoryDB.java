import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ImageRepositoryDB implements ImageRepository {

    private final static String INSERT_INTO_IMAGES = "INSERT INTO images (name, time, key, size) VALUES (?, ?, ?, ?)";
    private final static String SELECT_ALL_ORDER_BY_BY_SIZE_DESC = "SELECT * FROM images ORDER BY size DESC";
    private final DataSource dataSource;

    public ImageRepositoryDB(DataSource dataSource) {
        this.dataSource = dataSource;
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
            e.printStackTrace();
        }
    }

    @Override
    public List<ImageDisplayDto> getGlobalTop() {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SELECT_ALL_ORDER_BY_BY_SIZE_DESC)) {
            ResultSet resultSet = preparedStatement.executeQuery();
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
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
