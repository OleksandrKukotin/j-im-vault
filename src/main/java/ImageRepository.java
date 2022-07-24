import java.util.List;

public interface ImageRepository {

    void save(Image image);

    List<Image> getGlobalTop();

    List<Image> getTopBySizeRange(int from, int to);
}
