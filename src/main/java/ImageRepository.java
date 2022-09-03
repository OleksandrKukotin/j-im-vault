import java.util.List;

public interface ImageRepository {

    void save(Image image);

    List<Image> findAllImages();

    List<Image> findImagesInSizeRange(int from, int to);
}
