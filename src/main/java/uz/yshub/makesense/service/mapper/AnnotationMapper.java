package uz.yshub.makesense.service.mapper;

import org.mapstruct.*;
import uz.yshub.makesense.domain.*;
import uz.yshub.makesense.service.dto.*;

/**
 * Mapper for the entity {@link Annotation} and its DTO {@link AnnotationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnnotationMapper extends EntityMapper<AnnotationDTO, Annotation> {
    @Mapping(target = "bbox", source = "bbox", qualifiedByName = "bboxId")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    AnnotationDTO toDto(Annotation s);

    @Named("bboxId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BboxDTO toDtoBboxId(Bbox bbox);

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);

    @Named("imageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ImageDTO toDtoImageId(Image image);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);
}
