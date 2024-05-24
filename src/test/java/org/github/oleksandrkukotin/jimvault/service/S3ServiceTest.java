package org.github.oleksandrkukotin.jimvault.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class S3ServiceTest {

    private static final String BUCKET_NAME = "images";
    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpload() {
        InputStream inputStream = new ByteArrayInputStream("test content".getBytes());
        String key = s3Service.upload(inputStream);

        verify(amazonS3, times(1)).putObject(anyString(), eq(key), eq(inputStream), any(ObjectMetadata.class));
    }

    @Test
    void testGetAsBase64() throws Exception {
        String key = "test-key";
        String content = "test content";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        S3Object s3Object = mock(S3Object.class);
        S3ObjectInputStream s3ObjectInputStream = new S3ObjectInputStream(inputStream, null);

        when(amazonS3.getObject(anyString(), eq(key))).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(s3ObjectInputStream);

        String result = s3Service.getAsBase64(key);

        assertEquals(Base64.getEncoder().encodeToString(content.getBytes()), result);
    }
}
