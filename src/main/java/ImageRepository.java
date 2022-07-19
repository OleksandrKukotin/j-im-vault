import java.util.List;

public interface ImageRepository {

    // TODO: this is Java and not a python or ruby or something else, use full naming. Rename img to image
    void save(Image img);

    List<ImageDisplayDto> getGlobalTop();

    // TODO: rename min/max to from/to
    List<ImageDisplayDto> getTopBySizeRange(int min, int max);
}
