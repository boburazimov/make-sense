package uz.yshub.makesense.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yshub.makesense.domain.Segmentation;
import uz.yshub.makesense.repository.SegmentationRepository;
import uz.yshub.makesense.service.SegmentationService;
import uz.yshub.makesense.service.dto.SegmentationDTO;
import uz.yshub.makesense.service.mapper.SegmentationMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Segmentation}.
 */
@Service
@Transactional
public class SegmentationServiceImpl implements SegmentationService {

    private final Logger log = LoggerFactory.getLogger(SegmentationServiceImpl.class);

    private final SegmentationRepository segmentationRepository;

    private final SegmentationMapper segmentationMapper;

    public SegmentationServiceImpl(SegmentationRepository segmentationRepository, SegmentationMapper segmentationMapper) {
        this.segmentationRepository = segmentationRepository;
        this.segmentationMapper = segmentationMapper;
    }

    @Override
    public SegmentationDTO save(SegmentationDTO segmentationDTO) {
        log.debug("Request to save Segmentation : {}", segmentationDTO);
        Segmentation segmentation = segmentationMapper.toEntity(segmentationDTO);
        segmentation = segmentationRepository.save(segmentation);
        return segmentationMapper.toDto(segmentation);
    }

    @Override
    public SegmentationDTO update(SegmentationDTO segmentationDTO) {
        log.debug("Request to save Segmentation : {}", segmentationDTO);
        Segmentation segmentation = segmentationMapper.toEntity(segmentationDTO);
        segmentation = segmentationRepository.save(segmentation);
        return segmentationMapper.toDto(segmentation);
    }

    @Override
    public Optional<SegmentationDTO> partialUpdate(SegmentationDTO segmentationDTO) {
        log.debug("Request to partially update Segmentation : {}", segmentationDTO);

        return segmentationRepository
            .findById(segmentationDTO.getId())
            .map(existingSegmentation -> {
                segmentationMapper.partialUpdate(existingSegmentation, segmentationDTO);

                return existingSegmentation;
            })
            .map(segmentationRepository::save)
            .map(segmentationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SegmentationDTO> findAll() {
        log.debug("Request to get all Segmentations");
        return segmentationRepository.findAll().stream().map(segmentationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SegmentationDTO> findOne(Long id) {
        log.debug("Request to get Segmentation : {}", id);
        return segmentationRepository.findById(id).map(segmentationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Segmentation : {}", id);
        segmentationRepository.deleteById(id);
    }
}
