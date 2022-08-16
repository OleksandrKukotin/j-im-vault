import java.util.List;
import java.util.stream.Collectors;

public class ImageUtils {

    private ImageUtils(){

    }

    public static List<ImageDto> imagesToImageDtosConverter(List<Image> images, AmazonS3Service amazonS3Service) {
        return images.stream()
            .map(image -> new ImageDto(
                image,
                amazonS3Service.getImageAsBase64String(image.getKeyOfImageInS3Storage())
            ))
            .collect(Collectors.toList());
    }
}
