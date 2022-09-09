package uz.yshub.makesense.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.yshub.makesense.service.dto.SegmentationDTO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Segmentation.
 */
@Entity
@Table(name = "segmentation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Segmentation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "coordinate")
    private Double coordinate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bbox", "project", "image", "category" }, allowSetters = true)
    private Annotation annotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Segmentation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCoordinate() {
        return this.coordinate;
    }

    public Segmentation coordinate(Double coordinate) {
        this.setCoordinate(coordinate);
        return this;
    }

    public void setCoordinate(Double coordinate) {
        this.coordinate = coordinate;
    }

    public Annotation getAnnotation() {
        return this.annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Segmentation annotation(Annotation annotation) {
        this.setAnnotation(annotation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public Segmentation(SegmentationDTO segmentationDTO, Annotation annotation) {
        this.id = segmentationDTO.getId();
        this.coordinate = segmentationDTO.getCoordinate();
        this.annotation = annotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Segmentation)) {
            return false;
        }
        return id != null && id.equals(((Segmentation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Segmentation{" +
            "id=" + getId() +
            ", coordinate=" + getCoordinate() +
            "}";
    }
}
