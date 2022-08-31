package uz.yshub.makesense.service.mapper;

import org.mapstruct.*;
import uz.yshub.makesense.domain.Category;
import uz.yshub.makesense.service.dto.CategoryDTO;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {}
