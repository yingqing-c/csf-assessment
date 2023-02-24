package vttp2022.csf.assessment.server.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class S3Service {

    @Value("${BUCKET_NAME}")
    private String bucketName;

    @Value("${SPACES_ENDPOINT}")
    private String endpoint;

    @Autowired
    private AmazonS3 s3;

    public String getMapEndpoint() {
        return endpoint + "map/";
    }

    public S3Object getRestaurantMapImage(String restaurantId) {
        GetObjectRequest getReq = new GetObjectRequest(bucketName, "map/" + restaurantId);
        S3Object result = s3.getObject(getReq);
        ObjectMetadata metadata = result.getObjectMetadata();
        return result;
    }


    public String upload(MultipartFile file, String key) throws IOException {
        Map<String, String> userData = new HashMap<>();
        userData.put("key", key);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);

        PutObjectRequest putReq = new PutObjectRequest(bucketName,
                "map/%s".formatted(key), file.getInputStream(), metadata);

        // Make object publicly accessible
        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        // Upload file to S3 bucket
        s3.putObject(putReq);

        return endpoint + "map/" + key;
    }
}
