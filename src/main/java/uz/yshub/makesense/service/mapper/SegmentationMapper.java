package uz.yshub.makesense.service.mapper;

import org.mapstruct.*;
import uz.yshub.makesense.domain.Annotation;
import uz.yshub.makesense.domain.Segmentation;
import uz.yshub.makesense.service.dto.AnnotationDTO;
import uz.yshub.makesense.service.dto.SegmentationDTO;

/**
 * Mapper for the entity {@link Segmentation} and its DTO {@link SegmentationDTO}.
 */
@Mapper(componentModel = "spring")
public interface SegmentationMapper extends EntityMapper<SegmentationDTO, Segmentation> {
    @Mapping(target = "annotation", source = "annotation", qualifiedByName = "annotationId")
    SegmentationDTO toDto(Segmentation s);

    @Named("annotationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AnnotationDTO toDtoAnnotationId(Annotation annotation);
}
