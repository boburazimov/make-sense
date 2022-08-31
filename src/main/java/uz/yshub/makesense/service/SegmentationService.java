package uz.yshub.makesense.service;

import uz.yshub.makesense.service.dto.SegmentationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uz.yshub.makesense.domain.Segmentation}.
 */
public interface SegmentationService {
    /**
     * Save a segmentation.
     *
     * @param segmentationDTO the entity to save.
     * @return the persisted entity.
     */
    SegmentationDTO save(SegmentationDTO segmentationDTO);

    /**
     * Updates a segmentation.
     *
     * @param segmentationDTO the entity to update.
     * @return the persisted entity.
     */
    SegmentationDTO update(SegmentationDTO segmentationDTO);

    /**
     * Partially updates a segmentation.
     *
     * @param segmentationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SegmentationDTO> partialUpdate(SegmentationDTO segmentationDTO);

    /**
     * Get all the segmentations.
     *
     * @return the list of entities.
     */
    List<SegmentationDTO> findAll();

    /**
     * Get the "id" segmentation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SegmentationDTO> findOne(Long id);

    /**
     * Delete the "id" segmentation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
