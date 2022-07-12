import java.util.List;

public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void addToDB(ImageAddingFormDto imageAddingFormDto) {
        imageRepository.save(createImage(imageAddingFormDto));
    }

    public List<ImageDisplayDto> getGlobalTop() {
        return imageRepository.getGlobalTop();
    }

    public List<ImageDisplayDto> getTopBySizeRange(int min, int max) {
        return imageRepository.getTopBySizeRange(min, max);
    }

    private Image createImage(ImageAddingFormDto imageAddingFormDto) {
        final Image image = new Image();
        image.setName(imageAddingFormDto.getName());
        image.setTimeOfCreating(imageAddingFormDto.getTimeOfAdding());
        image.setKey(imageAddingFormDto.getKey());
        image.setSize(imageAddingFormDto.getSize());
        return image;
    }
}
