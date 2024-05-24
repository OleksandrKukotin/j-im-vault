package org.github.oleksandrkukotin.jimvault.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

public class S3Service {

    private static final String BUCKET = "images";
    private static final String ACCESS_KEY = "test";
    private static final String SECRET_KEY = "test";
    private static final String BUCKET_NAME = System.getenv().getOrDefault(BUCKET, BUCKET);
    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    private final AmazonS3 amazonS3;

    public S3Service() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
                System.getenv().getOrDefault(ACCESS_KEY, ACCESS_KEY),
                System.getenv().getOrDefault(SECRET_KEY, SECRET_KEY));
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("s3.localhost.localstack.cloud:4566",
                                Regions.EU_WEST_2.getName()
                        )
                )
                .build();
    }

    public String upload(InputStream inputStream) {
        String key = UUID.randomUUID().toString();
        amazonS3.putObject(BUCKET_NAME, key, inputStream, new ObjectMetadata());
        return key;
    }

    public String getAsBase64(String key) {
        try (S3ObjectInputStream s3ObjectInputStream = amazonS3.getObject(BUCKET_NAME, key).getObjectContent()) {
            return Base64.getEncoder().encodeToString(s3ObjectInputStream.getDelegateStream().readAllBytes());
        } catch (IOException e) {
            logger.error("An error occurred while reading a byte array");
            return "";
        }
    }
}
