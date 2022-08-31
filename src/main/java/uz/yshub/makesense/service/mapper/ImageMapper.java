package uz.yshub.makesense.service.mapper;

import org.mapstruct.*;
import uz.yshub.makesense.domain.Image;
import uz.yshub.makesense.service.dto.ImageDTO;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Mapper(componentModel = "spring")
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {}
