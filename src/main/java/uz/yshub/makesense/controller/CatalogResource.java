package uz.yshub.makesense.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yshub.makesense.controller.errors.BadRequestAlertException;
import uz.yshub.makesense.controller.utils.ResponseUtil;
import uz.yshub.makesense.domain.Catalog;
import uz.yshub.makesense.repository.CatalogRepository;
import uz.yshub.makesense.service.CatalogService;
import uz.yshub.makesense.service.dto.CatalogDTO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link uz.yshub.makesense.domain.Catalog}.
 */
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/catalogs")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Catalog", description = "The Catalog as Package API. Contains all the operations that can be performed on a catalog/package.")
public class CatalogResource {

    private final Logger log = LoggerFactory.getLogger(CatalogResource.class);
    private static final String ENTITY_NAME = "Catalog";
    private final CatalogService catalogService;
    private final CatalogRepository catalogRepository;

    /**
     * {@code POST  /catalogs} : Create a new catalog.
     *
     * @param catalog the catalog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catalog,
     * or with status {@code 400 (Bad Request)} if the catalog has already an ID.
     */
    @PostMapping
    public ResponseEntity<Catalog> createCatalog(@RequestBody CatalogDTO catalog) {
        log.debug("REST request to save Catalog : {}", catalog);

        if (catalog.getId() != null) {
            throw new BadRequestAlertException("A new catalog cannot already have an ID", ENTITY_NAME, "idExists");
        }

        if (catalog.getParentId() != null && !catalogRepository.existsById(catalog.getParentId())) {
            throw new BadRequestAlertException("A new catalog parent ID: '" + catalog.getParentId() + "' cannot be found!", ENTITY_NAME, "idNotFound");
        }

        Boolean existsCatalog = (catalog.getParentId() != null)
                ? catalogRepository.existsByNameAndParentId(catalog.getName(), catalog.getParentId())
                : catalogRepository.existsByNameAndParentIsNull(catalog.getName());

        if (existsCatalog) {
            throw new BadRequestAlertException("A new catalog name '" + catalog.getName() + "' already created under the parent ID: " + catalog.getParentId(), ENTITY_NAME, "sameNameExists");
        }


        Catalog result = catalogService.save(catalog);
        return ResponseEntity.ok().header(ENTITY_NAME, result.getName(), result.getId().toString()).body(result);
    }

    /**
     * {@code PUT  /catalog/:id} : Updates an existing catalog.
     *
     * @param id      the id of the catalog to save.
     * @param catalog the catalog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catalog,
     * or with status {@code 400 (Bad Request)} if the catalog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catalog couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Catalog> updateCatalog(@PathVariable(value = "id", required = false) final Long id, @RequestBody Catalog catalog) {
        log.debug("REST request to update Catalog : {}, {}", id, catalog);

        if (catalog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, catalog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!catalogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<Catalog> result = catalogService.update(catalog);
        return ResponseUtil.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /catalogs} : get all the catalogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catalogs in body.
     */
    @GetMapping
    public List<Catalog> getAllCatalogs() {
        log.debug("REST request to get all Catalogs");
        return catalogService.findAll();
    }

    /**
     * {@code GET /:id} : get the "id" catalog and child catalogs with images.
     *
     * @param id the id of the catalog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catalog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/img")
    @Operation(description = "Get all catalogs and imageUrls by catalog ID", method = "Get all catalogs and imageUrls by catalog ID")
    public ResponseEntity<Map<String, Object>> getCatalogsAndImages(@RequestParam(name = "id", required = false) final Long id) {
        log.debug("REST request to get Catalogs and Images by catalog ID: {}", id);

        if (id != null && !catalogRepository.existsById(id)) {
            throw new IllegalArgumentException("Given catalog ID: {}" + id + " is invalid!");
        }

        Map<String, Object> list = catalogService.getCatalogsAndImages(id);
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(list);
    }

    /**
     * {@code DELETE /:id} : delete the "id" catalog.
     *
     * @param id the id of the catalog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     * TODO: Fix delete endpoint.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatalog(@PathVariable Long id) {
        log.debug("REST request to delete Catalog : {}", id);
        catalogService.delete(id);
        return ResponseEntity.noContent().header(ENTITY_NAME, id.toString()).build();
    }
}
