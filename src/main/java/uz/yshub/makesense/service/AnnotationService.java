package uz.yshub.makesense.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.yshub.makesense.service.dto.AnnotationDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.yshub.makesense.domain.Annotation}.
 */
public interface AnnotationService {
    /**
     * Save a annotation.
     *
     * @param annotationDTO the entity to save.
     * @return the persisted entity.
     */
    AnnotationDTO save(AnnotationDTO annotationDTO);

    /**
     * Updates a annotation.
     *
     * @param annotationDTO the entity to update.
     * @return the persisted entity.
     */
    AnnotationDTO update(AnnotationDTO annotationDTO);

    /**
     * Partially updates a annotation.
     *
     * @param annotationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnnotationDTO> partialUpdate(AnnotationDTO annotationDTO);

    /**
     * Get all the annotations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnnotationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" annotation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnnotationDTO> findOne(Long id);

    /**
     * Delete the "id" annotation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
