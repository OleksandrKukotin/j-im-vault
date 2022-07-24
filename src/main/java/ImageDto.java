import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// TODO: you can convert into time format (if it is needed) outside of this DTO anf then remove this DTO and just use Image class  (if you dont need base64Image field!!!)
public class ImageDto {

    private static final String TIME_FORMAT = "dd.MM.yyyy HH:mm:ss"; // TODO: rename to DATE_TIME_FORMAT

    // TODO -- use "private final Image image" instead next 4 fields
    private final LocalDateTime creatingTimestamp;
    private final String name;
    private final String S3ObjectKey;
    private final int size;
    // TODO --

    // TODO: do you need the next field? Remove if it no needed
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

    // TODO: weird getter and it dont used (see first comment in this class)
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