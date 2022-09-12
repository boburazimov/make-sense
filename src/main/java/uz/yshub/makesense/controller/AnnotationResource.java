package uz.yshub.makesense.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import tech.jhipster.web.util.HeaderUtil;
//import tech.jhipster.web.util.PaginationUtil;
//import tech.jhipster.web.util.ResponseUtil;
import uz.yshub.makesense.controller.errors.BadRequestAlertException;
import uz.yshub.makesense.controller.utils.PaginationUtil;
import uz.yshub.makesense.controller.utils.ResponseUtil;
import uz.yshub.makesense.repository.AnnotationRepository;
import uz.yshub.makesense.service.AnnotationService;
import uz.yshub.makesense.service.dto.AnnotationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link uz.yshub.makesense.domain.Annotation}.
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Annotation", description = "The Annotation API. Contains all the operations that can be performed on a annotation.")
public class AnnotationResource {

    private final Logger log = LoggerFactory.getLogger(AnnotationResource.class);

    private static final String ENTITY_NAME = "annotation";

    private final AnnotationService annotationService;

    private final AnnotationRepository annotationRepository;

    public AnnotationResource(AnnotationService annotationService, AnnotationRepository annotationRepository) {
        this.annotationService = annotationService;
        this.annotationRepository = annotationRepository;
    }

    /**
     * {@code POST  /annotations} : Create a new annotation.
     *
     * @param annotationDTO the annotationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annotationDTO, or with status {@code 400 (Bad Request)} if the annotation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/annotations")
    public ResponseEntity<AnnotationDTO> createAnnotation(@RequestBody AnnotationDTO annotationDTO) throws URISyntaxException {
        log.debug("REST request to save Annotation : {}", annotationDTO);
        if (annotationDTO.getId() != null) {
            throw new BadRequestAlertException("A new annotation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnnotationDTO result = annotationService.save(annotationDTO);
        return ResponseEntity
            .created(new URI("/api/annotations/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /annotations/:id} : Updates an existing annotation.
     *
     * @param id the id of the annotationDTO to save.
     * @param annotationDTO the annotationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annotationDTO,
     * or with status {@code 400 (Bad Request)} if the annotationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the annotationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/annotations/{id}")
    public ResponseEntity<AnnotationDTO> updateAnnotation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnnotationDTO annotationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Annotation : {}, {}", id, annotationDTO);
        if (annotationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, annotationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!annotationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnnotationDTO result = annotationService.update(annotationDTO);
        return ResponseEntity
            .ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annotationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /annotations/:id} : Partial updates given fields of an existing annotation, field will ignore if it is null
     *
     * @param id the id of the annotationDTO to save.
     * @param annotationDTO the annotationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annotationDTO,
     * or with status {@code 400 (Bad Request)} if the annotationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the annotationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the annotationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/annotations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnnotationDTO> partialUpdateAnnotation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnnotationDTO annotationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Annotation partially : {}, {}", id, annotationDTO);
        if (annotationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, annotationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!annotationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnnotationDTO> result = annotationService.partialUpdate(annotationDTO);

        return ResponseUtil.wrapOrNotFound(
            result
//            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annotationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /annotations} : get all the annotations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annotations in body.
     */
    @GetMapping("/annotations")
    public ResponseEntity<List<AnnotationDTO>> getAllAnnotations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Annotations");
        Page<AnnotationDTO> page = annotationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /annotations/:id} : get the "id" annotation.
     *
     * @param id the id of the annotationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annotationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/annotations/{id}")
    public ResponseEntity<AnnotationDTO> getAnnotation(@PathVariable Long id) {
        log.debug("REST request to get Annotation : {}", id);
        Optional<AnnotationDTO> annotationDTO = annotationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(annotationDTO);
    }

    /**
     * {@code DELETE  /annotations/:id} : delete the "id" annotation.
     *
     * @param id the id of the annotationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/annotations/{id}")
    public ResponseEntity<Void> deleteAnnotation(@PathVariable Long id) {
        log.debug("REST request to delete Annotation : {}", id);
        annotationService.delete(id);
        return ResponseEntity
            .noContent()
//            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
