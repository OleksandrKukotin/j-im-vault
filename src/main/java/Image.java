import java.time.LocalDateTime;

public class Image {

    private final String name;
    private final LocalDateTime creatingTimestamp;
    private final String s3ObjectKey; // TODO: rename s3ObjectKey to more informative
    private final int size;

    public Image(String name, LocalDateTime creatingTimestamp, String s3ObjectKey, int size) {
        this.name = name;
        this.creatingTimestamp = creatingTimestamp;
        this.s3ObjectKey = s3ObjectKey;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatingTimestamp() {
        return creatingTimestamp;
    }

    public String getS3ObjectKey() {
        return s3ObjectKey;
    }

    public int getSize() {
        return size;
    }
}
