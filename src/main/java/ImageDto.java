import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImageDto {

    private static final String TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    private final LocalDateTime creatingTimestamp;
    private final String name;
    private final String S3ObjectKey;
    private final int size;
    private String base64Image;

    public ImageDto(LocalDateTime creatingTimestamp, String name, String S3ObjectKey, int size) {
        this.name = name;
        this.creatingTimestamp = creatingTimestamp;
        this.S3ObjectKey = S3ObjectKey;
        this.size = size;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getName() {
        return name;
    }

    public String getCreatingTimestamp() {
        return creatingTimestamp.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    public String getS3ObjectKey() {
        return S3ObjectKey;
    }

    public int getSize() {
        return size;
    }
}