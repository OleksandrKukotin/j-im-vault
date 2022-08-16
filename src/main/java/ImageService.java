import java.util.List;

public class ImageService {

    private final ImageRepositoryImpl imageRepositoryImpl;

    public ImageService(ImageRepositoryImpl imageRepositoryImpl) {
        this.imageRepositoryImpl = imageRepositoryImpl;
    }

    public void saveImage(Image image) {
        imageRepositoryImpl.save(image);
    }

    public List<Image> getGlobalTop() {
        return imageRepositoryImpl.getAllImages();
    }

    public List<Image> getTopBySizeRange(int from, int to) {
        return imageRepositoryImpl.getTopBySizeRange(from, to);
    }
}
