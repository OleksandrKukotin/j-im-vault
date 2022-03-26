import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageRepositoryDB implements ImageRepository {

    private final static String INSERT_INTO_IMAGES = "INSERT INTO images VALUES (?, ?)";
    private final DataSource dataSource;

    public ImageRepositoryDB(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Image img) {
        try {
            final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(INSERT_INTO_IMAGES);
            final FileInputStream fis = new FileInputStream(img.getImageFile());
            preparedStatement.setString(1, img.getImageName());
            preparedStatement.setBinaryStream(2, fis, img.getImageFile().length());
            preparedStatement.executeUpdate();

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
