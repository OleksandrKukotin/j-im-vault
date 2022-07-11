import java.time.LocalDateTime;

public class ImageAddingFormDto {

    private String name;
    private String key;
    private LocalDateTime timeOfAdding;

    public String getName() {
        return name;
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

    public LocalDateTime getTimeOfAdding() {
        return timeOfAdding;
    }

    public void setTimeOfAdding(LocalDateTime timeOfAdding) {
        this.timeOfAdding = timeOfAdding;
    }
}
