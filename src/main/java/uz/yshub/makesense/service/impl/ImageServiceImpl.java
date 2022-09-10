package uz.yshub.makesense.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.yshub.makesense.domain.Image;
import uz.yshub.makesense.repository.CatalogRepository;
import uz.yshub.makesense.repository.ImageRepository;
import uz.yshub.makesense.service.ImageService;
import uz.yshub.makesense.service.dto.ImageDTO;
import uz.yshub.makesense.service.mapper.ImageMapper;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service Implementation for managing {@link Image}.
 */
@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final MinioService minioService;
    private final CatalogRepository catalogRepository;

    @Value("${minio.image.small.name.suffix}")
    private String smallNameSuffix;

    @SneakyThrows
    @Override
    public List<ImageDTO> uploadImages(MultipartFile[] files, String bucket, String catalogId) {
        log.debug("Request to upload all files to uploadImages method");

        List<ImageDTO> uploads = new ArrayList<>();
        for (MultipartFile file : files) {
            uploads.add(uploadImage(file, bucket, catalogId));
        }
        return uploads;
    }

    @Override
    public ImageDTO uploadImage(MultipartFile multipartFile, String bucket, String catalogId) {
        log.debug("Request to upload one file to uploadImage method");

        Image image = createAttachment(multipartFile, catalogId);
        minioService.uploadFile(multipartFile, image, bucket);
        Image savedImage = imageRepository.save(image);
        log.debug("Response to upload one file to uploadImage method");
        return imageMapper.toDto(savedImage);
    }

    private Image createAttachment(MultipartFile multipartFile, String catalogId) {
        log.debug("Request to create attachment method!");
        Image attachment = new Image();
        try {
            // Get encoded + decoded original file name
            String originalFileName = encodeFileName(multipartFile.getOriginalFilename());

            // Get file extension/suffix from fileName (MyPhoto.jpg => jpg).
            String suffix = FilenameUtils.getExtension(originalFileName);

            // Generate unique name for file and ID for Image.
            UUID uniqueName = UUID.randomUUID();

            // Make dot + suffix.
            String dotSuffix = suffix.isEmpty() ? "" : '.' + suffix;

            // Make unique file name + suffix.
            String fileName = uniqueName + dotSuffix;

            // Make unique file name + key word of thumbnail image + suffix.
            String thumbnailFileName = uniqueName + smallNameSuffix + dotSuffix;

//            // Time prefix as package.
//            String timePrefix = new SimpleDateFormat("yyyy-MM").format(new Date());
//
//            // Make new full file name / path.
//            String packageAndFileName = timePrefix + File.separator + fileName;

            // Start to fill attachment model.
            attachment.setPath(fileName);
            attachment.setOriginalFileName(originalFileName);
            attachment.setFileName(fileName);
            attachment.setContentType(multipartFile.getContentType());
            attachment.setSuffix(suffix);
            attachment.setFileSize(String.valueOf(multipartFile.getSize()));
            attachment.setThumbnailFileName(thumbnailFileName);
            if (StringUtils.isNotEmpty(catalogId)) {
                catalogRepository.findById(Long.valueOf(catalogId)).ifPresent(attachment::setCatalog);
            }
            return attachment;
        } catch (Exception e) {
            throw new RuntimeException("Could not create the file. Error: " + e.getMessage());
        }
    }

    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(Objects.requireNonNull(multipart.getOriginalFilename()));
        multipart.transferTo(convFile);
        return convFile;
    }

    /**
     * Encoding name of the file to avoid duplicate file names
     *
     * @param originalFilename Name of the file
     * @return encoded file name in UTF-8 encoding
     * @throws UnsupportedEncodingException
     */
    private String encodeFileName(String originalFilename) {
        String fileName = null;
        try {
            fileName = URLEncoder.encode(originalFilename, "UTF-8");
            fileName = URLDecoder.decode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
