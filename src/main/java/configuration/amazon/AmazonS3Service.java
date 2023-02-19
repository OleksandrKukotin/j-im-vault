package configuration.amazon;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

public class AmazonS3Service {

    private static final String BUCKET = "bucket";
    private static final String ACCESS_KEY = "accessKey";
    private static final String SECRET_KEY = "secretKey";
    private static final String BUCKET_NAME = System.getenv().getOrDefault(BUCKET, BUCKET);
    private static final Logger LOGGER = Logger.getLogger(AmazonS3Service.class);

    private final AmazonS3 amazonS3;

    public AmazonS3Service() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
            System.getenv().getOrDefault(ACCESS_KEY, ACCESS_KEY),
            System.getenv().getOrDefault(SECRET_KEY, SECRET_KEY));
        this.amazonS3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(Regions.EU_WEST_2)
            .build();
    }

    public String upload(InputStream inputStream) {
        String key = UUID.randomUUID().toString();
        amazonS3.putObject(BUCKET_NAME, key, inputStream, new ObjectMetadata());
        return key;
    }

    // TODO: use this instead method above
    public String upload(File file) {
        String key = UUID.randomUUID().toString();
        amazonS3.putObject(BUCKET_NAME, key, file);
        return key;
    }

    public String getAsBase64(String key) {
        try (S3ObjectInputStream s3ObjectInputStream = amazonS3.getObject(BUCKET_NAME, key).getObjectContent()) {
            return Base64.getEncoder().encodeToString(s3ObjectInputStream.getDelegateStream().readAllBytes());
        } catch (IOException e) {
            LOGGER.error("An error occurred while reading a byte array");
            return "";
        }
    }
}
