package uz.yshub.makesense.service.mapper;

import org.mapstruct.*;
import uz.yshub.makesense.domain.Bbox;
import uz.yshub.makesense.service.dto.BboxDTO;

/**
 * Mapper for the entity {@link Bbox} and its DTO {@link BboxDTO}.
 */
@Mapper(componentModel = "spring")
public interface BboxMapper extends EntityMapper<BboxDTO, Bbox> {}
