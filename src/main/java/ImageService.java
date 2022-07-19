import java.util.List;

public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    // TODO: if you work with DB in the service, use the next worlds - create, update, get, delete/remove
    // TODO: so, rename it
    // TODO: also, don't use 'DB' in names because developers understand that is method from repository => so they work with DB
    public void addToDB(ImageAddingFormDto imageAddingFormDto) {
        imageRepository.save(createImage(imageAddingFormDto));
    }

    public List<ImageDisplayDto> getGlobalTop() {
        return imageRepository.getGlobalTop();
    }

    // TODO: rename min/max to from/to
    public List<ImageDisplayDto> getTopBySizeRange(int min, int max) {
        return imageRepository.getTopBySizeRange(min, max);
    }

    // TODO: get rid of this redundant method (after removed ImageAddingFormDto and used Image class instead)
    private Image createImage(ImageAddingFormDto imageAddingFormDto) {
        final Image image = new Image();
        image.setName(imageAddingFormDto.getName());
        image.setTimeOfCreating(imageAddingFormDto.getTimeOfAdding());
        image.setKey(imageAddingFormDto.getKey());
        image.setSize(imageAddingFormDto.getSize());
        return image;
    }
}
