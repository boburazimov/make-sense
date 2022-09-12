package uz.yshub.makesense.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yshub.makesense.domain.Catalog;
import uz.yshub.makesense.repository.CatalogRepository;
import uz.yshub.makesense.service.CatalogService;
import uz.yshub.makesense.service.ImageService;
import uz.yshub.makesense.service.dto.CatalogDTO;
import uz.yshub.makesense.service.dto.ImageCustomDTO;
import uz.yshub.makesense.service.mapper.CatalogMapper;
import uz.yshub.makesense.service.mapper.CategoryMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link uz.yshub.makesense.domain.Catalog}.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    private final Logger log = LoggerFactory.getLogger(CatalogServiceImpl.class);
    private final CatalogRepository catalogRepository;
    private final ImageService imageService;
    private final CatalogMapper catalogMapper;

    @Override
    public Catalog save(CatalogDTO catalogDTO) {
        log.debug("Request to save CatalogDTO : {}", catalogDTO);
        Catalog catalog = new Catalog();
        catalog.setName(catalogDTO.getName());
        if (catalogDTO.getParentId() != null && catalogRepository.existsById(catalogDTO.getParentId())) {
            // TODO: check catalog name to unique by in parent catalog.
            catalogRepository.findById(catalogDTO.getParentId()).ifPresent(catalog::setParent);
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public Optional<Catalog> update(Catalog catalog) {
        log.debug("Request to partially update Catalog: {}", catalog);

        return catalogRepository
                .findById(catalog.getId())
                .map(existingCatalog -> {
                    existingCatalog.setName(catalog.getName());
                    return existingCatalog;
                })
                .map(catalogRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Catalog> findAll() {
        log.debug("Request to get all Catalog");
        return catalogRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Catalog> findOne(Long id) {
        log.debug("Request to get Catalog : {}", id);
        return catalogRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Catalog : {}", id);
//        catalogRepository.findById(id).ifPresent(catalog -> {
//            catalogRepository.findAllByParentId()
//            boolean exists = catalogRepository.exists(catalog.getParent());
//
//        });
        catalogRepository.findById(id).ifPresent(catalogRepository::delete);
//        catalogRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getCatalogsAndImages(Long catalogId) {
        log.debug("Request to get child Catalogs and Images by getCatalogsAndImages method: {}", catalogId);

        boolean existId = catalogId != null && catalogRepository.existsById(catalogId);
        Map<String, Object> list = new HashMap<>();

        Optional<List<Catalog>> catalogList = existId
                ? catalogRepository.findAllByParentId(catalogId)
                : catalogRepository.findAllByParentIsNull();

        catalogList.ifPresent(catalogs -> list.put("catalogs", catalogs));

        List<ImageCustomDTO> imageList = imageService.getAllByCatalogId(catalogId);
        list.put("images", imageList);
        return list;
    }
}
