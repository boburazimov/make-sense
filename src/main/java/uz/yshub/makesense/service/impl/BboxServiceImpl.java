package uz.yshub.makesense.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yshub.makesense.domain.Bbox;
import uz.yshub.makesense.repository.BboxRepository;
import uz.yshub.makesense.service.BboxService;
import uz.yshub.makesense.service.dto.BboxDTO;
import uz.yshub.makesense.service.mapper.BboxMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Bbox}.
 */
@Service
@Transactional
public class BboxServiceImpl implements BboxService {

    private final Logger log = LoggerFactory.getLogger(BboxServiceImpl.class);

    private final BboxRepository bboxRepository;

    private final BboxMapper bboxMapper;

    public BboxServiceImpl(BboxRepository bboxRepository, BboxMapper bboxMapper) {
        this.bboxRepository = bboxRepository;
        this.bboxMapper = bboxMapper;
    }

    @Override
    public BboxDTO save(BboxDTO bboxDTO) {
        log.debug("Request to save Bbox : {}", bboxDTO);
        Bbox bbox = bboxMapper.toEntity(bboxDTO);
        bbox = bboxRepository.save(bbox);
        return bboxMapper.toDto(bbox);
    }

    @Override
    public BboxDTO update(BboxDTO bboxDTO) {
        log.debug("Request to save Bbox : {}", bboxDTO);
        Bbox bbox = bboxMapper.toEntity(bboxDTO);
        bbox = bboxRepository.save(bbox);
        return bboxMapper.toDto(bbox);
    }

    @Override
    public Optional<BboxDTO> partialUpdate(BboxDTO bboxDTO) {
        log.debug("Request to partially update Bbox : {}", bboxDTO);

        return bboxRepository
            .findById(bboxDTO.getId())
            .map(existingBbox -> {
                bboxMapper.partialUpdate(existingBbox, bboxDTO);

                return existingBbox;
            })
            .map(bboxRepository::save)
            .map(bboxMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BboxDTO> findAll() {
        log.debug("Request to get all Bboxes");
        return bboxRepository.findAll().stream().map(bboxMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BboxDTO> findOne(Long id) {
        log.debug("Request to get Bbox : {}", id);
        return bboxRepository.findById(id).map(bboxMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bbox : {}", id);
        bboxRepository.deleteById(id);
    }
}
