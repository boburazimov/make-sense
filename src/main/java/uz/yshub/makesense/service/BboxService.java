package uz.yshub.makesense.service;

import uz.yshub.makesense.service.dto.BboxDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uz.yshub.makesense.domain.Bbox}.
 */
public interface BboxService {
    /**
     * Save a bbox.
     *
     * @param bboxDTO the entity to save.
     * @return the persisted entity.
     */
    BboxDTO save(BboxDTO bboxDTO);

    /**
     * Updates a bbox.
     *
     * @param bboxDTO the entity to update.
     * @return the persisted entity.
     */
    BboxDTO update(BboxDTO bboxDTO);

    /**
     * Partially updates a bbox.
     *
     * @param bboxDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BboxDTO> partialUpdate(BboxDTO bboxDTO);

    /**
     * Get all the bboxes.
     *
     * @return the list of entities.
     */
    List<BboxDTO> findAll();

    /**
     * Get the "id" bbox.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BboxDTO> findOne(Long id);

    /**
     * Delete the "id" bbox.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
