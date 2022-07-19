import java.time.LocalDateTime;

public class Image {

    private final String name;
    private final LocalDateTime creatingTimestamp;
    private final String S3ObjectKey;
    private final int size;

    public Image(String name, LocalDateTime creatingTimestamp, String S3ObjectKey, int size) {
        this.name = name;
        this.creatingTimestamp = creatingTimestamp;
        this.S3ObjectKey = S3ObjectKey;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatingTimestamp() {
        return creatingTimestamp;
    }

    public String getS3ObjectKey() {
        return S3ObjectKey;
    }

    public int getSize() {
        return size;
    }
}
