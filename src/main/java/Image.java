import java.time.LocalDateTime;

public class Image {

    private final String name;
    private final LocalDateTime creatingTimestamp;
    private final String keyOfImageInS3Storage;
    private final int size;

    public Image(String name, LocalDateTime creatingTimestamp, String keyOfImageInS3Storage, int size) {
        this.name = name;
        this.creatingTimestamp = creatingTimestamp;
        this.keyOfImageInS3Storage = keyOfImageInS3Storage;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatingTimestamp() {
        return creatingTimestamp;
    }

    public String getKeyOfImageInS3Storage() {
        return keyOfImageInS3Storage;
    }

    public int getSize() {
        return size;
    }
}
