import java.time.LocalDateTime;

public class Image {

    private final String name;
    private final LocalDateTime creatingTimestamp;
    private final String imageKeyOnS3;
    private final int size;

    public Image(String name, LocalDateTime creatingTimestamp, String imageKeyOnS3, int size) {
        this.name = name;
        this.creatingTimestamp = creatingTimestamp;
        this.imageKeyOnS3 = imageKeyOnS3;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatingTimestamp() {
        return creatingTimestamp;
    }

    public String getImageKeyOnS3() {
        return imageKeyOnS3;
    }

    public int getSize() {
        return size;
    }
}
