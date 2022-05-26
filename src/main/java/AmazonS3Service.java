import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.InputStream;
import java.util.UUID;

public class AmazonS3Service {

    private AmazonS3 s3;
    private final static String BUCKET_NAME = "bucket" + UUID.randomUUID();
    private final static String ENDPOINT = "http://0.0.0.0:4566";

    public AmazonS3Service() {
        BasicAWSCredentials creds = new BasicAWSCredentials("aws-access-key", "aws-secret-key");
        this.s3 = AmazonS3Client.builder()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(ENDPOINT, Regions.EU_CENTRAL_1.getName()))
            .withCredentials(new AWSStaticCredentialsProvider(creds))
            .build();
    }

    public String addToS3(InputStream inputStream) {
        String objectKey = "key-" + UUID.randomUUID();
        this.s3.putObject(BUCKET_NAME, objectKey, inputStream, new ObjectMetadata());
        return objectKey;
    }

    protected InputStream getInputStreamFromS3(String key) {
        S3Object s3Object = this.s3.getObject(BUCKET_NAME, key);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        return s3ObjectInputStream.getDelegateStream();
    }

    private void removeFromS3(String key) {
        this.s3.deleteObject(BUCKET_NAME, key);
    }
}
