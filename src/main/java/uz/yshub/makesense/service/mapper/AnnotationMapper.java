package uz.yshub.makesense.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yshub.makesense.domain.*;
import uz.yshub.makesense.service.dto.*;

/**
 * Mapper for the entity {@link Annotation} and its DTO {@link AnnotationDTO}.
 */
@Service
@RequiredArgsConstructor
public class AnnotationMapper {

    private final BboxMapper bboxMapper;
    private final ProjectMapper projectMapper;
    private final ImageMapper imageMapper;
    private final CategoryMapper categoryMapper;

    public AnnotationDTO toDto(Annotation annotation) {

        BboxDTO bboxDTO;
        ProjectDTO projectDTO;
        ImageDTO imageDTO;
        CategoryDTO categoryDTO;

        if (annotation != null) {
            bboxDTO = annotation.getBbox() != null ? bboxMapper.toDto(annotation.getBbox()) : null;
            projectDTO = annotation.getProject() != null ? projectMapper.toDto(annotation.getProject()) : null;
            imageDTO = annotation.getImage() != null ? imageMapper.toDto(annotation.getImage()) : null;
            categoryDTO = annotation.getCategory() != null ? categoryMapper.toDto(annotation.getCategory()) : null;
            return new AnnotationDTO(annotation, bboxDTO, projectDTO, imageDTO, categoryDTO);
        }
        return null;
    }

    public Annotation toEntity(AnnotationDTO annotationDTO) {

        Bbox bbox;
        Project project;
        Image image;
        Category category;

        if (annotationDTO != null) {
            bbox = annotationDTO.getBbox() != null ? bboxMapper.toEntity(annotationDTO.getBbox()) : null;
            project = annotationDTO.getProject() != null ? projectMapper.toEntity(annotationDTO.getProject()) : null;
            image = annotationDTO.getImage() != null ? imageMapper.toEntity(annotationDTO.getImage()) : null;
            category = annotationDTO.getCategory() != null ? categoryMapper.toEntity(annotationDTO.getCategory()) : null;
            return new Annotation(annotationDTO, bbox, project, image, category);
        }
        return null;
    }

    public void partialUpdate(Annotation entity, AnnotationDTO dto) {

        if (dto == null) {
            return;
        }

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getIscrowd() != null) {
            entity.setIscrowd(dto.getIscrowd());
        }
        if (dto.getArea() != null) {
            entity.setArea(dto.getArea());
        }
        if (dto.getBbox() != null) {
            if (entity.getBbox() == null) {
                entity.bbox(new Bbox());
            }
            bboxMapper.partialUpdate(entity.getBbox(), dto.getBbox());
        }
        if (dto.getProject() != null) {
            if (entity.getProject() == null) {
                entity.project(new Project());
            }
            projectMapper.partialUpdate(entity.getProject(), dto.getProject());
        }
        if (dto.getImage() != null) {
            if (entity.getImage() == null) {
                entity.image(new Image());
            }
            imageMapper.partialUpdate(entity.getImage(), dto.getImage());
        }
    }
}
