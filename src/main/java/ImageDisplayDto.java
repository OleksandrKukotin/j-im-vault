import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// TODO: Image or Display? Rename to ImageDto
// TODO: Get rid of setters, use constructor for initialization
public class ImageDisplayDto {

    private String base64Image; // TODO: get rid of unused fields
    private String name;
    private LocalDateTime time;
    private String key; // TODO: rename to more clearly
    private int size;

    // TODO: constants need to be first in fields list (PAY ATTENTION on Java conventions!!)
    private final static String TIME_FORMAT = "dd.MM.yyyy HH:mm:ss"; // TODO: reorder modifiers (static final)

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}