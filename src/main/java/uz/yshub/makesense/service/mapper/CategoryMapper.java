package uz.yshub.makesense.service.mapper;

import org.springframework.stereotype.Service;
import uz.yshub.makesense.domain.Category;
import uz.yshub.makesense.service.dto.CategoryDTO;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Service
public class CategoryMapper {

    public CategoryDTO toDto(Category category) {
        if (category != null) {
            return new CategoryDTO(category);
        } else {
            return null;
        }
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO != null) {
            return new Category(categoryDTO);
        } else {
            return null;
        }
    }

    public void partialUpdate(Category entity, CategoryDTO dto) {

        if (dto == null) {
            return;
        }

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
    }
}
