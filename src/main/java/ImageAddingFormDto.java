import java.time.LocalDateTime;

public class ImageAddingFormDto {

    private String name;
    private String key;
    private LocalDateTime timeOfAdding;
    private int size;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
