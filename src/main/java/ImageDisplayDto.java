import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImageDisplayDto {

    private String base64Image;
    private String name;
    private LocalDateTime time;
    private String key;
    private final static String TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}