import java.util.List;

public class ImageService {

    private final PostgreSQLImageRepository postgreSQLImageRepository;

    public ImageService(PostgreSQLImageRepository postgreSQLImageRepository) {
        this.postgreSQLImageRepository = postgreSQLImageRepository;
    }

    public void saveImage(Image image) {
        postgreSQLImageRepository.save(image);
    }

    public List<Image> findAllImages() {
        return postgreSQLImageRepository.findAll();
    }

    public List<Image> findImagesInSizeRange(int from, int to) {
        return postgreSQLImageRepository.findImagesInSizeRange(from, to);
    }
}
