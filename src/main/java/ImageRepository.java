import java.util.List;

public interface ImageRepository {

    void save(Image img);

    List<ImageDisplayDto> getGlobalTop();

    List<ImageDisplayDto> getTopBySizeRange(int min, int max);
}
