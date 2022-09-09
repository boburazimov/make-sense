package uz.yshub.makesense.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yshub.makesense.domain.Catalog;
import uz.yshub.makesense.repository.CatalogRepository;
import uz.yshub.makesense.service.CatalogService;

import java.util.List;
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

    @Override
    public Catalog save(Catalog catalog) {
        log.debug("Request to save Catalog : {}", catalog);
        return catalogRepository.save(catalog);
    }

    @Override
    public Optional<Catalog> update(Catalog catalog) {
        log.debug("Request to partially update Catalog : {}", catalog);

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
        catalogRepository.deleteById(id);
    }
}
