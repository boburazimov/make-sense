package uz.yshub.makesense.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.yshub.makesense.domain.Image;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link Image} entity.
 */
@Schema(description = "Image entity.")
@Getter
@Setter
@NoArgsConstructor
public class ImageCustomDTO implements Serializable {

    private Long id;

    private String originalFileUrl;

    private String thumbnailFileUrl;

    private String originalFileName;

    private String fileName;

    private String contentType;

    private String fileSize;

    private Integer width;

    private Integer height;

    public ImageCustomDTO(Image image) {
        this.id = image.getId();
        this.originalFileName = image.getOriginalFileName();
        this.fileName = image.getFileName();
        this.contentType = image.getContentType();
        this.fileSize = image.getFileSize();
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageCustomDTO)) {
            return false;
        }

        ImageCustomDTO imageDTO = (ImageCustomDTO) o;
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
        return "ImageCustomDTO{" +
                "id=" + id +
                ", pathOriginalFile='" + originalFileUrl + '\'' +
                ", pathThumbnailFile='" + thumbnailFileUrl + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
