package uz.yshub.makesense.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.yshub.makesense.domain.*;
import uz.yshub.makesense.domain.enumeration.ProjectTypeEnum;
import uz.yshub.makesense.repository.*;
import uz.yshub.makesense.service.PointService;
import uz.yshub.makesense.service.dto.PointDto;

import java.util.List;
import java.util.Optional;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    AnnotationRepository annotationRepository;

    @Autowired
    BboxRepository bboxRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ImageRepository imageRepository;


    public String save(List<PointDto> pointDtos) {

        //project
        Project project = new Project();
        project.setDescription("point");
        project.setType(ProjectTypeEnum.POINT);

        projectRepository.save(project);

        for(PointDto pointDto : pointDtos) {
            Bbox bbox = new Bbox();
            Category category = new Category();
            Annotation annotation = new Annotation();
            //bbox
            bbox.setBboxX(pointDto.getPointX());
            bbox.setBboxY(pointDto.getPointY());
            bboxRepository.save(bbox);
            //category
            category.setName(pointDto.getLabel());
            categoryRepository.save(category);
            //annotation
            annotation.setProject(project);
            annotation.setBbox(bbox);
            annotation.setCategory(category);
            //image
            Optional<Image> image = imageRepository.findById(pointDto.getImageId());
            System.out.println(image.get());
            annotation.setImage(image.get());
            annotationRepository.save(annotation);
        }

        return "saved successfully...";
    }
}
