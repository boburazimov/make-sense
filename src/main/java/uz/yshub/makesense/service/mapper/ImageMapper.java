package uz.yshub.makesense.service.mapper;

import org.springframework.stereotype.Service;
import uz.yshub.makesense.domain.Image;
import uz.yshub.makesense.service.dto.ImageDTO;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Service
public class ImageMapper {

    public ImageDTO toDto(Image image) {
        if (image != null) {
            return new ImageDTO(image);
        } else {
            return null;
        }
    }

    public Image toEntity(ImageDTO imageDTO) {
        if (imageDTO != null) {
            return new Image(imageDTO);
        } else {
            return null;
        }
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
}
