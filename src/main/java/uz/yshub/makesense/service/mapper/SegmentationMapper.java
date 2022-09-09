package uz.yshub.makesense.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yshub.makesense.domain.*;
import uz.yshub.makesense.service.dto.AnnotationDTO;
import uz.yshub.makesense.service.dto.SegmentationDTO;

/**
 * Mapper for the entity {@link Segmentation} and its DTO {@link SegmentationDTO}.
 */
@Service
@RequiredArgsConstructor
public class SegmentationMapper {

    private final AnnotationMapper annotationMapper;

    public SegmentationDTO toDto(Segmentation segmentation) {

        AnnotationDTO annotationDTO;

        if (segmentation != null) {
            annotationDTO = segmentation.getAnnotation() != null ? annotationMapper.toDto(segmentation.getAnnotation()) : null;
            return new SegmentationDTO(segmentation, annotationDTO);
        }
        return null;
    }

    public Segmentation toEntity(SegmentationDTO segmentationDTO) {

        Annotation annotation;

        if (segmentationDTO != null) {
            annotation = segmentationDTO.getAnnotation() != null ? annotationMapper.toEntity(segmentationDTO.getAnnotation()) : null;
            return new Segmentation(segmentationDTO, annotation);
        }
        return null;
    }

    public void partialUpdate(Segmentation entity, SegmentationDTO dto) {

        if (dto == null) {
            return;
        }

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getCoordinate() != null) {
            entity.setCoordinate(dto.getCoordinate());
        }
        if (dto.getAnnotation() != null) {
            if (entity.getAnnotation() == null) {
                entity.annotation(new Annotation());
            }
            annotationMapper.partialUpdate(entity.getAnnotation(), dto.getAnnotation());
        }
    }
}
