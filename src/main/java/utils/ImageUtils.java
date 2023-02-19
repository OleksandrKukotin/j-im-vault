package utils;

import image.Image;
import image.ImageDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ImageUtils {

    private ImageUtils() {
        // hide public constructor
    }

    public static List<ImageDto> imagesToImageDtosMapper(
        List<Image> images,
        Function<String, String> imageTOBase64Function
    ) {
        return images.stream()
            .map(image -> new ImageDto(image, imageTOBase64Function.apply(image.imageKeyOnS3())))
            .collect(Collectors.toList());
    }
}
