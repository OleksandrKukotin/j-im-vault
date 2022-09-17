import java.time.format.DateTimeFormatter;

public class ImageDto {

    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    private final Image image;
    private final String base64Image;

    public ImageDto(Image image, String base64Image) {
        this.image = image;
        this.base64Image = base64Image;
    }

    public String getName() {
        return image.getName();
    }

    public String getFormattedCreatingTimestamp() {
        return image.getCreatingTimestamp().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public int getSize() {
        return image.getSize();
    }

    public String getBase64Image() {
        return base64Image;
    }
}