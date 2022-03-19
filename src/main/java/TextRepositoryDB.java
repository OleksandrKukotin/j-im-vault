import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TextRepositoryDB implements TextRepository {

    private static final String INSERT_INTO_TEXTS = "INSERT INTO texts(textAuthor,textBody)VALUES(?,?)";
    private static final String SELECT_TEXTS_BY_AUTHOR = "SELECT * FROM texts WHERE textAuthor";
    private final DataSource dataSource;

    public TextRepositoryDB(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Text text) {
        try {
            final PreparedStatement ps = dataSource.getConnection().prepareStatement(INSERT_INTO_TEXTS);
            ps.setString(1, text.getTextAuthor());
            ps.setString(2, text.getTextBody());
            ps.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
