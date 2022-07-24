import java.util.List;

public class ImageService {

    private final ImageRepositoryImplementation imageRepositoryImplementation;

    public ImageService(ImageRepositoryImplementation imageRepositoryImplementation) {
        this.imageRepositoryImplementation = imageRepositoryImplementation;
    }

    // TODO: dont use Repository in naming
    // TODO: classes which use this service shouldn't know about Repository!
    // TODO: rename to saveImage(). that's all.
    public void createInRepository(Image image) {
        imageRepositoryImplementation.save(image);
    }

    public List<ImageDto> getGlobalTop() {
        return imageRepositoryImplementation.getGlobalTop();
    }

    // TODO: you repeated the mistake - check previous code review and fix this!!!!
    public List<ImageDto> getTopBySizeRange(int min, int max) { // TODO: min/max -> from/to
        return imageRepositoryImplementation.getTopBySizeRange(min, max);
    }
}
