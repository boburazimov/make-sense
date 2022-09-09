package uz.yshub.makesense.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.yshub.makesense.domain.Image;
import uz.yshub.makesense.repository.ImageRepository;
import uz.yshub.makesense.service.ImageService;
import uz.yshub.makesense.service.dto.ImageDTO;
import uz.yshub.makesense.service.mapper.ImageMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @SneakyThrows
    @Override
    public List<ImageDTO> uploadImages(MultipartFile[] files) {
        log.debug("Request to upload all files to uploadImages method");

        List<ImageDTO> uploads = new ArrayList<>();

        for (MultipartFile file : files) {
            uploads.add(uploadImage(file));
        }
        return uploads;
    }

    @Override
    public ImageDTO uploadImage(MultipartFile multipartFile) {
        log.debug("Request to upload one file to uploadImage method");

        Image image = createAttachment(multipartFile);
        minioService.uploadFile(multipartFile, image);
        Image savedImage = imageRepository.save(image);
        log.debug("Response to upload one file to uploadImage method");
        return imageMapper.toDto(savedImage);
    }

    public Image createAttachment(MultipartFile multipartFile) {
        log.debug("Request to create attachment method!");
        Image attachment = new Image();
        try {
            // Get encoded + decoded original file name
            String originalFileName = encodeFileName(multipartFile.getOriginalFilename());

            // Get file extension/suffix from fileName (MyPhoto.jpg => jpg).
            String extension = FilenameUtils.getExtension(originalFileName);

            // Generate unique name for file and ID for Image.
            UUID uniqueName = UUID.randomUUID();

            // Get file name without extension from fileName (MyPhoto.jpg => MyPhoto).
            String fileName = uniqueName + (extension.isEmpty() ? "" : '.' + extension);

            // Time prefix as catalog.
            String timePrefix = new SimpleDateFormat("yyyy-MM").format(new Date());

            // Make new full file name / path.
            String fileNameAndExtension = timePrefix + File.separator + fileName;

//            File file = multipartToFile(multipartFile);
//            BufferedImage bimg = ImageIO.read(new ByteArrayInputStream(multipartFile.getBytes()));

            // Start to fill attachment model for write to Database
            attachment.setPath(fileNameAndExtension);
            attachment.setOriginalFileName(originalFileName);
            attachment.setFileName(fileName);
            attachment.setContentType(multipartFile.getContentType());
            attachment.setSuffix(extension);
            attachment.setFileSize(String.valueOf(multipartFile.getSize()));
            attachment.setWidth(1);
            attachment.setHeight(1);
            return attachment;
        } catch (Exception e) {
            throw new RuntimeException("Could not create the file. Error: " + e.getMessage());
        }
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
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
