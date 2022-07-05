import java.util.List;

public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void addToDB(ImageAddingFormDto imageAddingFormDto) {
        imageRepository.save(createImage(imageAddingFormDto));
    }

    public List<ImageDisplayDto> getAll() {
        return imageRepository.getAll();
    }

    private Image createImage(ImageAddingFormDto imageAddingFormDto) {
        final Image image = new Image();
        image.setImageName(imageAddingFormDto.getImageName());
        image.setKey(imageAddingFormDto.getKey());
        return image;
    }
}
