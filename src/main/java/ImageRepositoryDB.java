import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageRepositoryDB implements ImageRepository {

    private final static String INSERT_INTO_IMAGES = "INSERT INTO image_addr (imgname, key) VALUES (?, ?)";
    private final DataSource dataSource;

    public ImageRepositoryDB(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Image img, String key) {
        try {
            final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(INSERT_INTO_IMAGES);
            preparedStatement.setString(1, img.getImageName());
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
