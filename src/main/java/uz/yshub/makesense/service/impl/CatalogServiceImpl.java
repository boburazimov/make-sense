package uz.yshub.makesense.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.yshub.makesense.controller.errors.BadRequestAlertException;
import uz.yshub.makesense.domain.Catalog;
import uz.yshub.makesense.repository.CatalogRepository;
import uz.yshub.makesense.service.CatalogService;
import uz.yshub.makesense.service.ImageService;
import uz.yshub.makesense.service.dto.CatalogDTO;
import uz.yshub.makesense.service.dto.ImageCustomDTO;
import uz.yshub.makesense.service.mapper.CatalogMapper;
import uz.yshub.makesense.service.mapper.CategoryMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    private static final String ENTITY_NAME = "Catalog";

    private final Logger log = LoggerFactory.getLogger(CatalogServiceImpl.class);
    private final CatalogRepository catalogRepository;
    private final ImageService imageService;
    private final CatalogMapper catalogMapper;

    @Override
    public Catalog save(CatalogDTO catalogDTO) {
        log.debug("Request to save CatalogDTO : {}", catalogDTO);

        if (catalogDTO.getParentId() != null && !catalogRepository.existsById(catalogDTO.getParentId())) {
            throw new BadRequestAlertException("A new catalog parent ID: '" + catalogDTO.getParentId() + "' cannot be found!", ENTITY_NAME, "idNotFound");
        }

        Boolean existsCatalog = (catalogDTO.getParentId() != null)
                ? catalogRepository.existsByNameAndParentId(catalogDTO.getName(), catalogDTO.getParentId())
                : catalogRepository.existsByNameAndParentIsNull(catalogDTO.getName());

        if (existsCatalog) {
            throw new BadRequestAlertException("A new catalog name '" + catalogDTO.getName() + "' already created under the parent ID: " + catalogDTO.getParentId(), ENTITY_NAME, "sameNameExists");
        }

        Catalog catalog = new Catalog();
        catalog.setName(catalogDTO.getName());
        if (catalogDTO.getParentId() != null && catalogRepository.existsById(catalogDTO.getParentId())) {
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
        log.debug("Request to delete Catalog : {}", id); // 102
//        catalogRepository.findById(id).ifPresent(catalog -> { // 102 bor
//            catalog.setParent(null);
//            catalogRepository.findAllByParentId(catalog.getId()).ifPresent(catalogs -> );
//            boolean exists = catalogRepository.exists(catalog.getParent());
//        });
        catalogRepository.findById(id).ifPresent(catalogRepository::delete);
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


    private MultipartFile fileToMultipart(File file) throws IOException {
        log.debug("Request to convert from file to multipart");
        File file1 = new File(file.getAbsolutePath());
        FileInputStream input = new FileInputStream(file1);
        return new MockMultipartFile("file", file1.getName(), "image/jpeg", IOUtils.toByteArray(input));
    }

    @Override
    public void loadImages(String directoryName, Long catalogParenId) throws IOException {
        log.debug("Request to upload files from directory");

        File directory = new File(directoryName);

        File[] fList = directory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    MultipartFile multipartFile = fileToMultipart(file);
                    imageService.uploadImage(multipartFile, "test", catalogParenId != null ? String.valueOf(catalogParenId) : null);
                } else if (file.isDirectory()) {
                    Catalog savedCatalog = save(new CatalogDTO(file.getName(), catalogParenId));
                    loadImages(file.getAbsolutePath(), savedCatalog.getId());
                }
            }
        }
    }
}
