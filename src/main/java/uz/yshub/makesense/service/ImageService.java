package uz.yshub.makesense.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uz.yshub.makesense.service.dto.ImageCustomDTO;
import uz.yshub.makesense.service.dto.ImageDTO;

import java.util.List;

/**
 * Service Interface for managing {@link uz.yshub.makesense.domain.Image}.
 */
public interface ImageService {

    /**
     * Upload the images.
     * @param images Files to storage.
     * @param bucket Bucket name in minio.
     * @param catalogId catalogID of Catalog/Package.
     * @return List of ImageDTO.
     */
    List<ImageDTO> uploadImages(MultipartFile[] images, String bucket, String catalogId);

    /**
     * Upload the image.
     * @param image File to storage.
     * @param bucket Bucket name in minio.
     * @param catalogId catalogID of Catalog/Package.
     * @return Object of ImageDTO.
     */
    ImageDTO uploadImage(MultipartFile image, String bucket, String catalogId);

    /**
     * Get all images by exist Catalog ID or Catalog ID is null .
     * @param catalogId catalogID of Catalog/Package.
     * @return List of ImageCustomDTO.
     */
    List<ImageCustomDTO> getAllByCatalogId(Long catalogId);

    /**
     * Get all the images.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImageDTO> findAll(Pageable pageable);
}
