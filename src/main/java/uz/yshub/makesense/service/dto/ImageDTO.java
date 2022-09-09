package uz.yshub.makesense.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.yshub.makesense.domain.Catalog;
import uz.yshub.makesense.domain.Image;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.yshub.makesense.domain.Image} entity.
 */
@Schema(description = "Image entity.")
@Getter
@Setter
@NoArgsConstructor
public class ImageDTO implements Serializable {

    private Long id;

    private String path;

    private String originalFileName;

    private String fileName;

    private String contentType;

    private String suffix;

    private String fileSize;

    private Integer width;

    private Integer height;

    private String thumbnailFileName;

    private Catalog catalog;

    public ImageDTO(Image image) {
        this.id = image.getId();
        this.path = image.getPath();
        this.originalFileName = image.getOriginalFileName();
        this.fileName = image.getFileName();
        this.contentType = image.getContentType();
        this.suffix = image.getSuffix();
        this.fileSize = image.getFileSize();
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.thumbnailFileName = image.getThumbnailFileName();
        this.catalog = image.getCatalog();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageDTO)) {
            return false;
        }

        ImageDTO imageDTO = (ImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, imageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", suffix='" + suffix + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", thumbnailFileName='" + thumbnailFileName + '\'' +
                ", catalog=" + catalog +
                '}';
    }
}
