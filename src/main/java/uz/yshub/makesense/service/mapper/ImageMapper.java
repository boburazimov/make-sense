package uz.yshub.makesense.service.mapper;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import uz.yshub.makesense.domain.Image;
import uz.yshub.makesense.service.dto.ImageCustomDTO;
import uz.yshub.makesense.service.dto.ImageDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Service
public class ImageMapper {

    public ImageDTO toDto(Image image) {
        return ObjectUtils.isNotEmpty(image) ? new ImageDTO(image) : null;
    }

    public ImageCustomDTO toCustomDto(Image image){
        return ObjectUtils.isNotEmpty(image) ? new ImageCustomDTO(image) : null;
    }

    public Image toEntity(ImageDTO imageDTO) {
        return ObjectUtils.isNotEmpty(imageDTO) ? new Image(imageDTO) : null;
    }

    public void partialUpdate(Image entity, ImageDTO dto) {

        if (dto == null) {
            return;
        }

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getPath() != null) {
            entity.setPath(dto.getPath());
        }
        if (dto.getOriginalFileName() != null) {
            entity.setOriginalFileName(dto.getOriginalFileName());
        }
        if (dto.getFileName() != null) {
            entity.setFileName(dto.getFileName());
        }
        if (dto.getContentType() != null) {
            entity.setContentType(dto.getContentType());
        }
        if (dto.getFileSize() != null) {
            entity.setFileSize(dto.getFileSize());
        }
        if (dto.getWidth() != null) {
            entity.setWidth(dto.getWidth());
        }
        if (dto.getHeight() != null) {
            entity.setHeight(dto.getHeight());
        }
    }

    public List<ImageCustomDTO> toCustomDtos(List<Image> images) {
        return images.stream().map(this::toCustomDto).collect(Collectors.toList());
    }
}
