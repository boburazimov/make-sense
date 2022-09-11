package uz.yshub.makesense.service.mapper;

import org.springframework.stereotype.Service;
import uz.yshub.makesense.domain.Catalog;
import uz.yshub.makesense.service.dto.CatalogDTO;

/**
 * Mapper for the entity {@link Catalog} and its DTO {@link CatalogDTO}.
 */
@Service
public class CatalogMapper {

    public CatalogDTO toDto(Catalog catalog) {
        return catalog != null ? new CatalogDTO(catalog) : null;
    }
}
