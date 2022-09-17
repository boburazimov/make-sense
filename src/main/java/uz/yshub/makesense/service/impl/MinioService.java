package uz.yshub.makesense.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.yshub.makesense.controller.utils.ImageUtils;
import uz.yshub.makesense.domain.Image;
import uz.yshub.makesense.service.dto.FileModel;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class MinioService {

    private final Logger log = LoggerFactory.getLogger(MinioService.class);
    private final MinioClient minioClient;

    @Value("${minio.endpoint}")
    private String BaseUrl;

    @SneakyThrows
    public void uploadFile(MultipartFile multipartFile, Image image, String bucket) {
        log.debug("Request to upload one file in by MinioClient");

        // Make 'bucketDefaultName' bucket if not exist.
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            // Make a new bucket.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }

        String bucketPolicy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucket).build());
        System.out.println(bucketPolicy);


        // Make builder file for put Object into Minio.
        PutObjectArgs.Builder putObjectArgs = makePutObjectArgs(multipartFile, image, bucket);

        // Upload 'fileName' as object name 'fileNameAndExtension' to bucket.
        finalUploadImage(putObjectArgs, image.getFileName(), bucket);

//        String s = cloudinaryService(image);

        BufferedImage thumbnailBufferedImage = makeThumbnailImageFromStoredImage(bucket, image);

        // Make builder for put resized Object into Minio.
        PutObjectArgs.Builder resizedPutObjectArgs = makePutObjectArgsByBufferedImage(thumbnailBufferedImage, image, bucket);

        // Upload resized image 'fileName' as object name 'fileNameAndExtension' to bucket 'bucketDefaultName'.
        finalUploadImage(resizedPutObjectArgs, image.getThumbnailFileName(), bucket);
    }

    @SneakyThrows
    private void finalUploadImage(PutObjectArgs.Builder putObject, String fileName, String bucket){
        minioClient.putObject(putObject.build());
        log.debug("Image success saved: " + fileName + ". To bucket: " + bucket);
    }

    private String cloudinaryService(Image image){

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dtr8fw41y",
                "api_key", "988982831456151",
                "api_secret", "OLgxvpcry6oqQoYKkYWI47Aybf0"));


//        String minioFileUrl = BaseUrl + File.separator + image.getPath();
        String minioFileUrl = "http://176.96.241.203:9000/makesense/0d947cba-74af-4cc5-ab00-4daa39e74af0.jpeg";

        Map params = ObjectUtils.asMap(
                "public_id", image.getThumbnailFileName(),
                "overwrite", true
        );

        try {
            Map uploadResult = cloudinary.uploader().upload(minioFileUrl, params);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        cloudinary.url().transformation(new Transformation().height(200).width(200).crop("limit")).imageTag("recipes/cashew_chicken.jpg");
        String thumbUrl = cloudinary.url()
                .transformation(new Transformation().width(200).crop("thumb"))
                .publicId(image.getThumbnailFileName()).generate(image.getThumbnailFileName());

        return null;
    }


    private BufferedImage makeThumbnailImageFromStoredImage(String bucket, Image image) throws IOException {
        log.debug("Request to makeThumbnailImageFromStoredImage method MinioClient");

        // Get object from minio.
        InputStream savedImageInputStream = getObjectByBucketNameAndFileName(bucket, image.getFileName());

        ImageInputStream imageInputStream = ImageIO.createImageInputStream(savedImageInputStream);

        // Create BufferedImage
        BufferedImage bufferedImage = ImageIO.read(imageInputStream);

        // Fill last two field of Image.
        image.setWidth(bufferedImage.getWidth());
        image.setHeight(bufferedImage.getHeight());

        // Resize image and return new resizedImage.
        return ImageUtils.resizeImage(bufferedImage);
    }

    /**
     * Get inputStream from minio by bucketName and fileName.
     * @param bucketName type of String.
     * @param fileName type of String.
     * @return the {@link InputStream}
     */
    @SneakyThrows
    public InputStream getObjectByBucketNameAndFileName(String bucketName, String fileName) {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build());
    }

    @SneakyThrows
    public PutObjectArgs.Builder makePutObjectArgsByBufferedImage(BufferedImage bufferedImage, Image image, String bucketName) {
        log.debug("Request to make PutObjectArgsByBufferedImage builder");

        Map<String, String> headers = generalMakePutObjectArgs(image);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, image.getSuffix(), os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

        PutObjectArgs.Builder builder;
        builder = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(image.getThumbnailFileName())
                .contentType(image.getContentType())
                .headers(headers)
                .tags(headers)
                .stream(inputStream, inputStream.available(), -1);
        inputStream.close();
        return builder;
    }

    private Map<String, String> generalMakePutObjectArgs(Image image) {
        Map<String, String> headers = new ConcurrentHashMap<>();
        headers.put(FileModel.size, image.getFileSize() + "");
        headers.put(FileModel.name, image.getFileName());
        headers.put(FileModel.oldName, image.getOriginalFileName());
        headers.put(FileModel.contentType, image.getContentType());
        headers.put(FileModel.suffix, image.getSuffix());
        headers.put(FileModel.uploadDate, new Date().toString());
        return headers;
    }

    @SneakyThrows
    public PutObjectArgs.Builder makePutObjectArgs(MultipartFile multipartFile, Image image, String bucketDefaultName) {
        log.debug("Request to make PutObjectArgs builder");

        Map<String, String> headers = generalMakePutObjectArgs(image);
        PutObjectArgs.Builder builder;
        builder = PutObjectArgs.builder()
                .bucket(bucketDefaultName)
                .object(image.getFileName())
                .contentType(image.getContentType())
                .headers(headers)
                .tags(headers)
                .stream(multipartFile.getInputStream(), Long.parseLong(image.getFileSize()), -1);
        return builder;
    }
}
