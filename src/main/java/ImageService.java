import java.util.List;

public class ImageService {

    private final ImageRepositoryImpl imageRepositoryImpl;

    public ImageService(ImageRepositoryImpl imageRepositoryImpl) {
        this.imageRepositoryImpl = imageRepositoryImpl;
    }

    public void saveImage(Image image) {
        imageRepositoryImpl.save(image);
    }

    public List<Image> findAllImages() {
        return imageRepositoryImpl.findAllImages();
    }

    public List<Image> findImagesInSizeRange(int from, int to) {
        return imageRepositoryImpl.findImagesInSizeRange(from, to);
    }
}
