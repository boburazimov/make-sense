package uz.yshub.makesense.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import tech.jhipster.web.util.HeaderUtil;
//import tech.jhipster.web.util.ResponseUtil;
import uz.yshub.makesense.controller.errors.BadRequestAlertException;
import uz.yshub.makesense.controller.utils.ResponseUtil;
import uz.yshub.makesense.repository.SegmentationRepository;
import uz.yshub.makesense.service.SegmentationService;
import uz.yshub.makesense.service.dto.SegmentationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link uz.yshub.makesense.domain.Segmentation}.
 */
@RestController
@RequestMapping("/api")
public class SegmentationResource {

    private final Logger log = LoggerFactory.getLogger(SegmentationResource.class);

    private static final String ENTITY_NAME = "segmentation";

    private final SegmentationService segmentationService;

    private final SegmentationRepository segmentationRepository;

    public SegmentationResource(SegmentationService segmentationService, SegmentationRepository segmentationRepository) {
        this.segmentationService = segmentationService;
        this.segmentationRepository = segmentationRepository;
    }

    /**
     * {@code POST  /segmentations} : Create a new segmentation.
     *
     * @param segmentationDTO the segmentationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new segmentationDTO, or with status {@code 400 (Bad Request)} if the segmentation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/segmentations")
    public ResponseEntity<SegmentationDTO> createSegmentation(@RequestBody SegmentationDTO segmentationDTO) throws URISyntaxException {
        log.debug("REST request to save Segmentation : {}", segmentationDTO);
        if (segmentationDTO.getId() != null) {
            throw new BadRequestAlertException("A new segmentation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SegmentationDTO result = segmentationService.save(segmentationDTO);
        return ResponseEntity
            .created(new URI("/api/segmentations/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /segmentations/:id} : Updates an existing segmentation.
     *
     * @param id the id of the segmentationDTO to save.
     * @param segmentationDTO the segmentationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentationDTO,
     * or with status {@code 400 (Bad Request)} if the segmentationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the segmentationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/segmentations/{id}")
    public ResponseEntity<SegmentationDTO> updateSegmentation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SegmentationDTO segmentationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Segmentation : {}, {}", id, segmentationDTO);
        if (segmentationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SegmentationDTO result = segmentationService.update(segmentationDTO);
        return ResponseEntity
            .ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /segmentations/:id} : Partial updates given fields of an existing segmentation, field will ignore if it is null
     *
     * @param id the id of the segmentationDTO to save.
     * @param segmentationDTO the segmentationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentationDTO,
     * or with status {@code 400 (Bad Request)} if the segmentationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the segmentationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the segmentationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/segmentations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SegmentationDTO> partialUpdateSegmentation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SegmentationDTO segmentationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Segmentation partially : {}, {}", id, segmentationDTO);
        if (segmentationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SegmentationDTO> result = segmentationService.partialUpdate(segmentationDTO);

        return ResponseUtil.wrapOrNotFound(
            result
//            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /segmentations} : get all the segmentations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of segmentations in body.
     */
    @GetMapping("/segmentations")
    public List<SegmentationDTO> getAllSegmentations() {
        log.debug("REST request to get all Segmentations");
        return segmentationService.findAll();
    }

    /**
     * {@code GET  /segmentations/:id} : get the "id" segmentation.
     *
     * @param id the id of the segmentationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the segmentationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/segmentations/{id}")
    public ResponseEntity<SegmentationDTO> getSegmentation(@PathVariable Long id) {
        log.debug("REST request to get Segmentation : {}", id);
        Optional<SegmentationDTO> segmentationDTO = segmentationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(segmentationDTO);
    }

    /**
     * {@code DELETE  /segmentations/:id} : delete the "id" segmentation.
     *
     * @param id the id of the segmentationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/segmentations/{id}")
    public ResponseEntity<Void> deleteSegmentation(@PathVariable Long id) {
        log.debug("REST request to delete Segmentation : {}", id);
        segmentationService.delete(id);
        return ResponseEntity
            .noContent()
//            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
