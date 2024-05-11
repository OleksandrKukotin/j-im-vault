package org.github.oleksandrkukotin.jimvault.image;

import java.time.format.DateTimeFormatter;

public class ImageDto {

    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    private final Image image;
    private final String base64Image;

    public ImageDto(Image image, String base64Image) {
        this.image = image;
        this.base64Image = base64Image;
    }

    // Disclaimer : methods below used in JSP files
    public String getName() {
        return image.name();
    }

    public String getFormattedCreatingTimestamp() {
        return image.creatingTimestamp().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public int getSize() {
        return image.size();
    }

    public String getBase64Image() {
        return base64Image;
    }
}