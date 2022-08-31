package uz.yshub.makesense.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.yshub.makesense.domain.Annotation} entity.
 */
public class AnnotationDTO implements Serializable {

    private Long id;

    private Boolean iscrowd;

    private Double area;

    private BboxDTO bbox;

    private ProjectDTO project;

    private ImageDTO image;

    private CategoryDTO category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIscrowd() {
        return iscrowd;
    }

    public void setIscrowd(Boolean iscrowd) {
        this.iscrowd = iscrowd;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public BboxDTO getBbox() {
        return bbox;
    }

    public void setBbox(BboxDTO bbox) {
        this.bbox = bbox;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnotationDTO)) {
            return false;
        }

        AnnotationDTO annotationDTO = (AnnotationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, annotationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnnotationDTO{" +
            "id=" + getId() +
            ", iscrowd='" + getIscrowd() + "'" +
            ", area=" + getArea() +
            ", bbox=" + getBbox() +
            ", project=" + getProject() +
            ", image=" + getImage() +
            ", category=" + getCategory() +
            "}";
    }
}
