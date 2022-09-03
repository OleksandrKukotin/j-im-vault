import java.time.format.DateTimeFormatter;

public class ImageDto {

    private static final String TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    private final Image image;
    private final String base64Image;

    public ImageDto(Image image, String base64Image) {
        this.image = image;
        this.base64Image = base64Image;
    }

    public String getName() {
        return this.image.getName();
    }

    public String getFormattedCreatingTimestamp() {
        return this.image.getCreatingTimestamp().format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    public int getSize() {
        return this.image.getSize();
    }

    public String getBase64Image() {
        return base64Image;
    }
}