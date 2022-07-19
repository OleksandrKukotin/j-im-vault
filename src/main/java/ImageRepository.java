import java.util.List;

public interface ImageRepository {

    void save(Image image);

    List<ImageDto> getGlobalTop();

    List<ImageDto> getTopBySizeRange(int from, int to);
}
