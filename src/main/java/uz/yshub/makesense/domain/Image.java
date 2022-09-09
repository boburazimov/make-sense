package uz.yshub.makesense.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.yshub.makesense.service.dto.ImageDTO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Image entity.
 */
@Entity
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

    @ManyToOne
    private Catalog catalog;

    public Image() {}

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Image id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public Image path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOriginalFileName() {
        return this.originalFileName;
    }

    public Image originalFileName(String originalFileName) {
        this.setOriginalFileName(originalFileName);
        return this;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Image fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Image contentType(String contentType) {
        this.setContentType(contentType);
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public Image fileSize(String fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Image width(Integer width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Image height(Integer height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getThumbnailFileName() {
        return thumbnailFileName;
    }

    public void setThumbnailFileName(String thumbnailFileName) {
        this.thumbnailFileName = thumbnailFileName;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

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
