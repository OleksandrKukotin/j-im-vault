package image;

import java.util.List;

public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    public List<Image> findAllImages() {
        return imageRepository.findAll();
    }

    public List<Image> findImagesInSizeRange(int from, int to) {
        return imageRepository.findImagesInSizeRange(from, to);
    }
}
