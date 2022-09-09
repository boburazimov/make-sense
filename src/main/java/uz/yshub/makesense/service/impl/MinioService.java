package uz.yshub.makesense.service.impl;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.yshub.makesense.config.MinioConfig;
import uz.yshub.makesense.domain.Image;
import uz.yshub.makesense.init.ImageUtils;
import uz.yshub.makesense.service.dto.FileModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class MinioService {

    private final Logger log = LoggerFactory.getLogger(MinioService.class);
    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    @Value("${minio.image.resize.width}")
    private int width;
    @Value("${minio.image.resize.height}")
    private int height;

    @SneakyThrows
    public Image uploadFile(MultipartFile multipartFile, Image image, String bucket) {
        log.debug("Request to upload one file in by MinioClient");

        // Make 'bucketDefaultName' bucket if not exist.
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            // Make a new bucket.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }

        // Make builder file for put Object into Minio.
        PutObjectArgs.Builder putObjectArgs = makePutObjectArgs(multipartFile, image, bucket);

        // Upload 'fileName' as object name 'fileNameAndExtension' to bucket 'bucketDefaultName'.
        minioClient.putObject(putObjectArgs.build());

        BufferedImage thumbnailBufferedImage = makeThumbnailImageFromStoredImage(bucket, image);

        // Make builder for put resized Object into Minio.
        PutObjectArgs.Builder resizedPutObjectArgs = makePutObjectArgsByBufferedImage(thumbnailBufferedImage, image, bucket);

        // Upload resized image 'fileName' as object name 'fileNameAndExtension' to bucket 'bucketDefaultName'.
        minioClient.putObject(resizedPutObjectArgs.build());

        // Fill last two field of Image.
        image.setWidth(thumbnailBufferedImage.getWidth());
        image.setHeight(thumbnailBufferedImage.getHeight());
        return image;
    }

    @SneakyThrows
    private BufferedImage makeThumbnailImageFromStoredImage(String bucket, Image image) {
        log.debug("Request to makeThumbnailImageFromStoredImage method MinioClient");

        // Get object from minio.
        InputStream savedImageInputStream = getObjectByBucketNameAndFileName(bucket, image.getPath());

        // Create BufferredImage
        BufferedImage bimg = ImageIO.read(savedImageInputStream);

        // Resize image and return new resizedImage.
        return ImageUtils.resizeImage(bimg, width, height);
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
    public PutObjectArgs.Builder makePutObjectArgsByBufferedImage(BufferedImage bufferedImage, Image image, String bucketDefaultName) {
        log.debug("Request to make PutObjectArgsByBufferedImage builder");

        Map<String, String> headers = generalMakePutObjectArgs(image);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, image.getSuffix(), os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

        PutObjectArgs.Builder builder;
        builder = PutObjectArgs.builder()
                .bucket(bucketDefaultName)
                .object(image.getPath())
                .contentType(image.getContentType())
                .headers(headers)
                .tags(headers)
                .stream(inputStream, inputStream.available(), -1);
        return builder;
    }

    private Map<String, String> generalMakePutObjectArgs(Image image) {
        Map<String, String> headers = new ConcurrentHashMap<>();
        headers.put(FileModel.size, image.getFileSize() + "");
        headers.put(FileModel.name, image.getPath());         // файл новое имя
        headers.put(FileModel.oldName, image.getOriginalFileName());   // исходное имя файла
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
                .object(image.getPath())
                .contentType(image.getContentType())
                .headers(headers)
                .tags(headers)
                .stream(multipartFile.getInputStream(), Long.parseLong(image.getFileSize()), -1);
        return builder;
    }
}
