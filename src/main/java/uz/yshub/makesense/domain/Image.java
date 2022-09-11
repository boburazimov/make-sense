package uz.yshub.makesense.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.yshub.makesense.service.dto.ImageDTO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Image entity.
 */
@Entity
@Getter
@Setter
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Image extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "original_file_name")
    private String originalFileName;

    /* File stored in Server under this name. */
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "content_type")
    private String contentType;

    /* Extension of file (jpg, png, etc) */
    @Column(name = "suffix")
    private String suffix;

    @Column(name = "file_size")
    private String fileSize;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    /* Resized name of Original Image */
    @Column(name = "thumbnail_file_name")
    private String thumbnailFileName;

    @Column(name = "bucket_name")
    private String bucketName;

    @ManyToOne
    private Catalog catalog;

    public Image() {}

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Image(ImageDTO imageDTO) {
        this.id = imageDTO.getId();
        this.path = imageDTO.getPath();
        this.originalFileName = imageDTO.getOriginalFileName();
        this.fileName = imageDTO.getFileName();
        this.contentType = imageDTO.getContentType();
        this.suffix = imageDTO.getSuffix();
        this.fileSize = imageDTO.getFileSize();
        this.width = imageDTO.getWidth();
        this.height = imageDTO.getHeight();
        this.thumbnailFileName = imageDTO.getThumbnailFileName();
        this.bucketName = imageDTO.getBucketName();
        this.catalog = imageDTO.getCatalog();
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        return id != null && id.equals(((Image) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", originalFileName='" + getOriginalFileName() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", suffix='" + getSuffix() + "'" +
            ", fileSize='" + getFileSize() + "'" +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            "}";
    }
}
