import java.io.InputStream;

public class Image {

    private String imageName;
    private InputStream imageIS;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImageIS() {
        return imageIS;
    }

    public void setImageIS(InputStream imageIS) {
        this.imageIS = imageIS;
    }
}
