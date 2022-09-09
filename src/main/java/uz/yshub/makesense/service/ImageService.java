package uz.yshub.makesense.service;

import org.springframework.web.multipart.MultipartFile;
import uz.yshub.makesense.service.dto.ImageDTO;

import java.util.List;

/**
 * Service Interface for managing {@link uz.yshub.makesense.domain.Image}.
 */
public interface ImageService {

    /**
     * Upload the images.
     * @param images Files to storage
     * @return List of ImageDTO
     */
    List<ImageDTO> uploadImages(MultipartFile[] images);

    /**
     * Upload the image.
     * @param image File to storage
     * @return Object of ImageDTO.
     */
    ImageDTO uploadImage(MultipartFile image);

}
