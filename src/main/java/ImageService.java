public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public boolean addToDB(ImageAddingFormDto imageAddingFormDto) {
        imageRepository.save(createImage(imageAddingFormDto));
        return true;
    }

    private Image createImage(ImageAddingFormDto imageAddingFormDto) {
        final Image image = new Image();
        image.setImageName(imageAddingFormDto.getImageName());
        image.setImageFile(imageAddingFormDto.getImageFile());
        return image;
    }
}
