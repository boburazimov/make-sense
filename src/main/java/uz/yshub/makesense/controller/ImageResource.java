package uz.yshub.makesense.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.yshub.makesense.config.MinioConfig;
import uz.yshub.makesense.controller.errors.BadRequestAlertException;
import uz.yshub.makesense.controller.utils.PaginationUtil;
import uz.yshub.makesense.service.ImageService;
import uz.yshub.makesense.service.dto.ApiResponseDTO;
import uz.yshub.makesense.service.dto.ImageDTO;

import java.util.List;

/**
 * REST controller for managing {@link uz.yshub.makesense.domain.Image}.
 */
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Image", description = "The Image API. Contains all the operations that can be performed on a image.")
public class ImageResource {

    private final Logger log = LoggerFactory.getLogger(ImageResource.class);

    private final ImageService imageService;
    private final MinioConfig minioConfig;

    /**
     * {@code POST  /images} : Upload images.
     *
     * @param files the array of images.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imageDTO.
     * @throws BadRequestAlertException if the files array are empty (Null).
     */
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDTO> uploadFiles(
            @RequestPart(value = "files") MultipartFile[] files,
            @RequestParam(value = "bucket", required = false) String bucket,
            @RequestParam(value = "catalogId", required = false) String catalogId) {
        log.debug("REST request to upload an images");

        if (files == null) {
            throw new BadRequestAlertException("File must not be null!", "imageManagement", "fileNullPointer");
        }

        bucket = (StringUtils.isEmpty(bucket)) ? minioConfig.getBucketDefaultName() : bucket;
        try {
            List<ImageDTO> images = imageService.uploadImages(files, bucket, catalogId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO(true, "Uploaded the images successfully", images));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponseDTO(false, e.getMessage()));
        }
    }

    /**
     * {@code GET  /images} : get all the images.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of images in body.
     */
    @GetMapping("/images")
    public ResponseEntity<List<ImageDTO>> getAllImages(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Images");
        Page<ImageDTO> page = imageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /images/:id} : get the "id" image.
     *
     * @param id the id of the imageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageDTO, or with status {@code 404 (Not Found)}.
     */
//    @GetMapping("/images/{id}")
//    public ResponseEntity<ImageDTO> getImage(@PathVariable Long id) {
//        log.debug("REST request to get Image : {}", id);
//        Optional<ImageDTO> imageDTO = imageService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(imageDTO);
//    }

    /**
     * {@code DELETE  /images/:id} : delete the "id" image.
     *
     * @param id the id of the imageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
//    @DeleteMapping("/images/{id}")
//    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
//        log.debug("REST request to delete Image : {}", id);
//        imageService.delete(id);
//        return ResponseEntity
//                .noContent()
//                .build();
//    }
}
