import java.util.List;

public interface ImageRepository {

    void save(Image image);

    List<ImageDisplayDto> getGlobalTop();

    List<ImageDisplayDto> getTopBySizeRange(int min, int max);
}
