import java.sql.ResultSet;

public interface ImageRepository {

    void save(Image img, String key);

    ResultSet getAll();
}
