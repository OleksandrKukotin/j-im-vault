package org.github.oleksandrkukotin.jimvault.utils;

import org.github.oleksandrkukotin.jimvault.image.Image;
import org.github.oleksandrkukotin.jimvault.image.ImageDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ImageUtils {

    private ImageUtils() {
        // hide public constructor
    }

    public static List<ImageDto> imagesToImageDtosMapper(
        List<Image> images,
        Function<String, String> imageToBase64Function
    ) {
        return images.stream()
            .map(image -> new ImageDto(image, imageToBase64Function.apply(image.imageKeyOnS3())))
            .collect(Collectors.toList());
    }
}
