import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageRepositoryDB implements ImageRepository {

    private final static String INSERT_INTO_IMAGES = "INSERT INTO image_addr (imgname, key) VALUES (?, ?)";
    private final static String SELECT_ALL_IMAGES = "SELECT * FROM image_addr";
    private final DataSource dataSource;

    public ImageRepositoryDB(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Image img) {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(INSERT_INTO_IMAGES)) {
            preparedStatement.setString(1, img.getImageName());
            preparedStatement.setString(2, img.getKey());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getAll() {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SELECT_ALL_IMAGES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> imageNames = new ArrayList<>();
            while (resultSet.next()) {
                imageNames.add(resultSet.getString("imgname"));
            }
            return imageNames;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
