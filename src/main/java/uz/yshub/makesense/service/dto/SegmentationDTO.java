package uz.yshub.makesense.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.yshub.makesense.domain.Segmentation} entity.
 */
public class SegmentationDTO implements Serializable {

    private Long id;

    private Double coordinate;

    private AnnotationDTO annotation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Double coordinate) {
        this.coordinate = coordinate;
    }

    public AnnotationDTO getAnnotation() {
        return annotation;
    }

    public void setAnnotation(AnnotationDTO annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SegmentationDTO)) {
            return false;
        }

        SegmentationDTO segmentationDTO = (SegmentationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, segmentationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SegmentationDTO{" +
            "id=" + getId() +
            ", coordinate=" + getCoordinate() +
            ", annotation=" + getAnnotation() +
            "}";
    }
}
