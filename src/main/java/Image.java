import java.time.LocalDateTime;

// TODO: get rid of setters, use constructor for fields initialization
// TODO: make fields as 'final'
public class Image {

    private String name;
    private LocalDateTime timeOfCreating; // TODO: rename to creatingTimestamp
    private String key; // TODO: rename to more clearly
    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTimeOfCreating() {
        return timeOfCreating;
    }

    public void setTimeOfCreating(LocalDateTime timeOfCreating) {
        this.timeOfCreating = timeOfCreating;
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
