import java.util.List;

public class ImageService {

    private final ImageRepositoryImplementation imageRepositoryImplementation;

    public ImageService(ImageRepositoryImplementation imageRepositoryImplementation) {
        this.imageRepositoryImplementation = imageRepositoryImplementation;
    }

    public void createInRepository(Image image) {
        imageRepositoryImplementation.save(image);
    }

    public List<ImageDto> getGlobalTop() {
        return imageRepositoryImplementation.getGlobalTop();
    }

    public List<ImageDto> getTopBySizeRange(int min, int max) {
        return imageRepositoryImplementation.getTopBySizeRange(min, max);
    }
}
