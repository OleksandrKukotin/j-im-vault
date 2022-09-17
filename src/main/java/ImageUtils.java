import java.util.List;
import java.util.stream.Collectors;

public class ImageUtils {

    private ImageUtils() {
        // hide public constructor
    }

    public static List<ImageDto> imagesToImageDtosMapper(List<Image> images, AmazonS3Service amazonS3Service) {
        return images.stream()
            .map(image -> new ImageDto(image, amazonS3Service.getImageAsBase64(image.getImageKeyOnS3())))
            .collect(Collectors.toList());
    }
}
