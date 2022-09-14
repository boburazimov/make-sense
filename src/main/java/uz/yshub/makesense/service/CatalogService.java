package uz.yshub.makesense.service;

import uz.yshub.makesense.domain.Catalog;
import uz.yshub.makesense.service.dto.CatalogDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link uz.yshub.makesense.domain.Catalog}.
 */
public interface CatalogService {

    /**
     * Save a catalog.
     *
     * @param catalog the entity to save.
     * @return the persisted entity.
     */
    Catalog save(CatalogDTO catalog);

    /**
     * Updates a catalog.
     *
     * @param catalog the entity to update.
     * @return the persisted entity.
     */
    Optional<Catalog> update(Catalog catalog);

    /**
     * Get all the catalogs.
     *
     * @return the list of entities.
     */
    List<Catalog> findAll();

    /**
     * Get the "id" catalog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Catalog> findOne(Long id);

    /**
     * Delete the "id" catalog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all child catalog and images by the "id" catalog.
     *
     * @param id the id of the entity.
     * @return {@link Map} with catalogs and images.
     */
    Map<String, Object> getCatalogsAndImages(Long id);

    /**
     * load images from folder to db
     *
     * @param uploads
     * @param catalogParenId
     * @throws IOException
     */
    void loadImages(String uploads, Long catalogParenId) throws IOException;
}
