public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public boolean addToDB(ImageAddingFormDto imageAddingFormDto, String key) {
        imageRepository.save(createImage(imageAddingFormDto), key);
        return true;
    }

    private Image createImage(ImageAddingFormDto imageAddingFormDto) {
        final Image image = new Image();
        image.setImageName(imageAddingFormDto.getImageName());
        image.setImageIS(imageAddingFormDto.getImageIS());
        return image;
    }
}
