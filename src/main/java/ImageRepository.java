import java.util.List;

public interface ImageRepository {

    void save(Image img);

    List<String> getAll();
}
