package uz.yshub.makesense.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yshub.makesense.domain.Annotation;
import uz.yshub.makesense.domain.enumeration.ProjectTypeEnum;
import uz.yshub.makesense.repository.AnnotationRepository;
import uz.yshub.makesense.repository.ImageRepository;
import uz.yshub.makesense.service.AnnotationService;
import uz.yshub.makesense.service.MainService;
import uz.yshub.makesense.service.ProjectService;
import uz.yshub.makesense.service.SegmentationService;
import uz.yshub.makesense.service.dto.*;
import uz.yshub.makesense.service.mapper.ImageMapper;
import uz.yshub.makesense.service.mapper.ProjectMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing annotations.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class MainServiceImpl implements MainService {

    private final Logger log = LoggerFactory.getLogger(MainServiceImpl.class);

    private final SegmentationService segmentationService;
    private final ImageRepository imageRepository;
    private final AnnotationService annotationService;
    private final AnnotationRepository annotationRepository;
    private final ImageMapper imageMapper;
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @Override
    public ApiResponse save(MainDTO mainDTO, ProjectTypeEnum projectTypeEnum) {
        log.debug("Request to create new Project by annotations: {}", mainDTO);

        final ProjectDTO projectDTO = (mainDTO.getInfo() != null && mainDTO.getInfo().getDescription() != null)
                ? projectService.save(projectMapper.toDtoFromMainDto(mainDTO.getInfo(), projectTypeEnum)) : null;

        final List<CategoryDTO> categoryDTOS = (mainDTO.getCategories() != null && !mainDTO.getCategories().isEmpty())
                ? mainDTO.getCategories() : null;

        if (mainDTO.getAnnotations() != null && !mainDTO.getAnnotations().isEmpty()) {

            List<Annotation> annotationList = mainDTO.getAnnotations().stream().map(mainAnnotationDTO -> {

                AnnotationDTO annotationDTO = new AnnotationDTO();
                annotationDTO.setArea(mainAnnotationDTO.getArea());
                annotationDTO.setIscrowd(mainAnnotationDTO.getIscrowd() > 0);
                annotationDTO.setProject(projectDTO);

                if (mainAnnotationDTO.getBbox() != null) {
                    BboxDTO bboxDTO = new BboxDTO();
                    List<Double> bbox = mainAnnotationDTO.getBbox();
                    for (int i = 0; i < bbox.size(); i++){
                        switch (i) {
                            case 0 : bboxDTO.setBboxX(bbox.get(0)); break;
                            case 1 : bboxDTO.setBboxY(bbox.get(1)); break;
                            case 2 : bboxDTO.setBboxWidth(bbox.get(2)); break;
                            case 3 : bboxDTO.setBboxHeght(bbox.get(3)); break;
                            default:
                                break;
                        }
                    }
                    annotationDTO.setBbox(bboxDTO);
                }

                if (mainAnnotationDTO.getCategory_id() != 0) {
                    assert categoryDTOS != null;
                    categoryDTOS.stream()
                            .filter(categoryDTO1 -> categoryDTO1.getId() == mainAnnotationDTO.getCategory_id()).findFirst()
                            .ifPresent(categoryDTO1 -> annotationDTO.setCategory(
                                    new CategoryDTO(categoryDTO1.getName(),
                                            categoryDTO1.getDescription(), categoryDTO1.getColorCode())));
                }

                boolean imageExistById = mainAnnotationDTO.getImage_id() != null && imageRepository.existsById(mainAnnotationDTO.getImage_id());

                if (imageExistById) {
                    imageRepository.findById(mainAnnotationDTO.getImage_id())
                            .ifPresent(image -> annotationDTO.setImage(imageMapper.toDto(image)));
                }
                AnnotationDTO savedAnnotationDTO = annotationService.save(annotationDTO);

                if (mainAnnotationDTO.getSegmentation() != null && !mainAnnotationDTO.getSegmentation().get(0).isEmpty()) {
                    mainAnnotationDTO.getSegmentation().get(0)
                            .forEach(coordinate -> segmentationService
                                    .save(new SegmentationDTO(coordinate, savedAnnotationDTO)));
                }

                final Annotation[] finalSavedAnnotation = {null};
                if (mainAnnotationDTO.getImage_id() != null && imageExistById) {
                    imageRepository.findById(mainAnnotationDTO.getImage_id()).ifPresent(image -> {
                        annotationRepository.findById(savedAnnotationDTO.getId()).ifPresent(annotation -> {
                            annotation.setImage(image);
                            finalSavedAnnotation[0] = annotationRepository.save(annotation);
                        });
                    });
                }
                return finalSavedAnnotation[0];
            }).collect(Collectors.toList());
            return new ApiResponse(true, "Annotations saved successfully!", annotationList);
        }
        return new ApiResponse(false, "Error in save Annotations");
    }
}
