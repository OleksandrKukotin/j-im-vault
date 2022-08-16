import java.util.List;

public interface ImageRepository {

    void save(Image image);

    List<Image> getAllImages();

    List<Image> getTopBySizeRange(int from, int to);
}
