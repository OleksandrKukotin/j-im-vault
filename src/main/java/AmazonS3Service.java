import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

public class AmazonS3Service {

    private static final String ACCESS_KEY_ENV = "accessKey";
    private static final String SECRET_KEY_ENV = "secretKey";
    private static final String BUCKET_NAME = System.getenv().getOrDefault("bucket", "bucket");
    private static final Logger log = Logger.getLogger(AmazonS3Service.class);
    private final AmazonS3 s3;

    public AmazonS3Service() {
        final BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
            System.getenv().getOrDefault(ACCESS_KEY_ENV, ACCESS_KEY_ENV),
            System.getenv().getOrDefault(SECRET_KEY_ENV, SECRET_KEY_ENV));
        this.s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(Regions.EU_WEST_2)
            .build();
    }

    public String addToS3(InputStream inputStream) {
        final String objectKey = "key-" + UUID.randomUUID();
        this.s3.putObject(BUCKET_NAME, objectKey, inputStream, new ObjectMetadata());
        return objectKey;
    }

    protected String getImageAsBase64String(String key) {
        try (final S3ObjectInputStream s3ObjectInputStream = this.s3.getObject(BUCKET_NAME, key).getObjectContent()) {
            return Base64.getEncoder().encodeToString(s3ObjectInputStream.getDelegateStream().readAllBytes());
        } catch (IOException e) {
            log.error("An error occurred while reading a byte array");
            return null;
        }
    }
}
