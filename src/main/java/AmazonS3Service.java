import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class AmazonS3Service {

    private AmazonS3 s3;
    private final static String BUCKET_NAME = System.getenv().getOrDefault("bucket", "bucket");

    public AmazonS3Service() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(
            System.getenv().getOrDefault("accessKey", "accessKey"),
            System.getenv().getOrDefault("secretKey", "secretKey"));
        this.s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.EU_WEST_2)
            .build();
    }

    public String addToS3(InputStream inputStream) {
        String objectKey = "key-" + UUID.randomUUID();
        this.s3.putObject(BUCKET_NAME, objectKey, inputStream, new ObjectMetadata());
        return objectKey;
    }

    protected byte[] getImageAsBytes(String key) throws IOException {
        S3Object s3Object = this.s3.getObject(BUCKET_NAME, key);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        return s3ObjectInputStream.getDelegateStream().readAllBytes();
    }

    private void removeFromS3(String key) {
        this.s3.deleteObject(BUCKET_NAME, key);
    }
}
