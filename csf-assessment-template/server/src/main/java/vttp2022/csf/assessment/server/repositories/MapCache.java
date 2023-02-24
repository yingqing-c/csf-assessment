package vttp2022.csf.assessment.server.repositories;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import vttp2022.csf.assessment.server.models.LatLng;
import vttp2022.csf.assessment.server.services.S3Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Repository
public class MapCache {

    @Autowired
    private S3Service s3Service;

    // TODO Task 4
    // Use this method to retrieve the map
    // You can add any parameters (if any) and the return type
    // DO NOT CHNAGE THE METHOD'S NAME
    public String getMap(String restaurantId, LatLng coord) throws IOException {
        // check if image in S3
        S3Object image = s3Service.getRestaurantMapImage(restaurantId);
        try (S3ObjectInputStream is = image.getObjectContent()) {
            return s3Service.getMapEndpoint() + image.getKey();
        } catch (AmazonS3Exception ex) {
            // If key is not found

            s3Service.upload(getImageFromURL(coord), restaurantId);
        } catch (Exception ex) {
            // For S3ObjectInputStream
            System.out.println(ex);
            return null;
        }
        return null;
    }

    // You may add other methods to this class
    public MultipartFile getImageFromURL(LatLng coord) {
        // retrieve map from http://map.chuklee.com with the following request
        //		GET/map?lat=<latitude>&lng=<longitude>
        //		Accept: image/png
        // coord=long,lat
        // save byte array image to s3 bucket
        // include map as an attribute in the restaurant doc
        String url = UriComponentsBuilder
                .fromUriString("http://map.chuklee.com")
                .queryParam("lat", coord.getLatitude())
                .queryParam("lng", coord.getLongitude())
                .toUriString();
        RequestEntity req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        byte[] map = resp.getBody().getBytes();
        return new ByteArrayMultipartFile(map);
    }

    class ByteArrayMultipartFile implements MultipartFile {
        private final byte[] bytes;

        ByteArrayMultipartFile(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return null;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return bytes;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(bytes);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {

        }
    }
}