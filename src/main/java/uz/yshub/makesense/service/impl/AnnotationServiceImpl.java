package uz.yshub.makesense.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yshub.makesense.domain.Annotation;
import uz.yshub.makesense.repository.AnnotationRepository;
import uz.yshub.makesense.service.AnnotationService;
import uz.yshub.makesense.service.dto.AnnotationDTO;
import uz.yshub.makesense.service.mapper.AnnotationMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Annotation}.
 */

@Service
@Transactional
public class AnnotationServiceImpl implements AnnotationService {

    private final Logger log = LoggerFactory.getLogger(AnnotationServiceImpl.class);

    @Autowired
    AnnotationRepository annotationRepository;
    @Autowired
    AnnotationMapper annotationMapper;

//    public AnnotationServiceImpl(AnnotationRepository annotationRepository, AnnotationMapper annotationMapper) {
//        this.annotationRepository = annotationRepository;
//        this.annotationMapper = annotationMapper;
//    }

    @Override
    public AnnotationDTO save(AnnotationDTO annotationDTO) {
        log.debug("Request to save Annotation : {}", annotationDTO);
        Annotation annotation = annotationMapper.toEntity(annotationDTO);
        annotation = annotationRepository.save(annotation);
        return annotationMapper.toDto(annotation);
    }

    @Override
    public AnnotationDTO update(AnnotationDTO annotationDTO) {
        log.debug("Request to save Annotation : {}", annotationDTO);
        Annotation annotation = annotationMapper.toEntity(annotationDTO);
        annotation = annotationRepository.save(annotation);
        return annotationMapper.toDto(annotation);
    }

    @Override
    public Optional<AnnotationDTO> partialUpdate(AnnotationDTO annotationDTO) {
        log.debug("Request to partially update Annotation : {}", annotationDTO);

        return annotationRepository
            .findById(annotationDTO.getId())
            .map(existingAnnotation -> {
                annotationMapper.partialUpdate(existingAnnotation, annotationDTO);

                return existingAnnotation;
            })
            .map(annotationRepository::save)
            .map(annotationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnotationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Annotations");
        return annotationRepository.findAll(pageable).map(annotationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnnotationDTO> findOne(Long id) {
        log.debug("Request to get Annotation : {}", id);
        return annotationRepository.findById(id).map(annotationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Annotation : {}", id);
        annotationRepository.deleteById(id);
    }
}
