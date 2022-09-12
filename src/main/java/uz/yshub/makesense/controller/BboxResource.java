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
import uz.yshub.makesense.repository.BboxRepository;
import uz.yshub.makesense.service.BboxService;
import uz.yshub.makesense.service.dto.BboxDTO;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link uz.yshub.makesense.domain.Bbox}.
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class BboxResource {

    private final Logger log = LoggerFactory.getLogger(BboxResource.class);

    private static final String ENTITY_NAME = "bbox";

    private final BboxService bboxService;

    private final BboxRepository bboxRepository;

    public BboxResource(BboxService bboxService, BboxRepository bboxRepository) {
        this.bboxService = bboxService;
        this.bboxRepository = bboxRepository;
    }

    /**
     * {@code POST  /bboxes} : Create a new bbox.
     *
     * @param bboxDTO the bboxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bboxDTO, or with status {@code 400 (Bad Request)} if the bbox has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bboxes")
    public ResponseEntity<BboxDTO> createBbox(@RequestBody BboxDTO bboxDTO) throws URISyntaxException {
        log.debug("REST request to save Bbox : {}", bboxDTO);
        if (bboxDTO.getId() != null) {
            throw new BadRequestAlertException("A new bbox cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BboxDTO result = bboxService.save(bboxDTO);

        return ResponseEntity
            .created(new URI("/api/bboxes/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bboxes/:id} : Updates an existing bbox.
     *
     * @param id the id of the bboxDTO to save.
     * @param bboxDTO the bboxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bboxDTO,
     * or with status {@code 400 (Bad Request)} if the bboxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bboxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bboxes/{id}")
    public ResponseEntity<BboxDTO> updateBbox(@PathVariable(value = "id", required = false) final Long id, @RequestBody BboxDTO bboxDTO)
        throws URISyntaxException {
        log.debug("REST request to update Bbox : {}, {}", id, bboxDTO);
        if (bboxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bboxDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bboxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BboxDTO result = bboxService.update(bboxDTO);
        return ResponseEntity
            .ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bboxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bboxes/:id} : Partial updates given fields of an existing bbox, field will ignore if it is null
     *
     * @param id the id of the bboxDTO to save.
     * @param bboxDTO the bboxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bboxDTO,
     * or with status {@code 400 (Bad Request)} if the bboxDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bboxDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bboxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bboxes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BboxDTO> partialUpdateBbox(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BboxDTO bboxDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bbox partially : {}, {}", id, bboxDTO);
        if (bboxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bboxDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bboxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BboxDTO> result = bboxService.partialUpdate(bboxDTO);

        return ResponseUtil.wrapOrNotFound(
            result
//            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bboxDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bboxes} : get all the bboxes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bboxes in body.
     */
    @GetMapping("/bboxes")
    public List<BboxDTO> getAllBboxes() {
        log.debug("REST request to get all Bboxes");
        return bboxService.findAll();
    }

    /**
     * {@code GET  /bboxes/:id} : get the "id" bbox.
     *
     * @param id the id of the bboxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bboxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bboxes/{id}")
    public ResponseEntity<BboxDTO> getBbox(@PathVariable Long id) {
        log.debug("REST request to get Bbox : {}", id);
        Optional<BboxDTO> bboxDTO = bboxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bboxDTO);
    }

    /**
     * {@code DELETE  /bboxes/:id} : delete the "id" bbox.
     *
     * @param id the id of the bboxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bboxes/{id}")
    public ResponseEntity<Void> deleteBbox(@PathVariable Long id) {
        log.debug("REST request to delete Bbox : {}", id);
        bboxService.delete(id);
        return ResponseEntity
            .noContent()
//            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
