package uz.yshub.makesense.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Bbox.
 */
@Entity
@Table(name = "bbox")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bbox implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bbox_x")
    private Double bboxX;

    @Column(name = "bbox_y")
    private Double bboxY;

    @Column(name = "bbox_width")
    private Double bboxWidth;

    @Column(name = "bbox_heght")
    private Double bboxHeght;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bbox id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBboxX() {
        return this.bboxX;
    }

    public Bbox bboxX(Double bboxX) {
        this.setBboxX(bboxX);
        return this;
    }

    public void setBboxX(Double bboxX) {
        this.bboxX = bboxX;
    }

    public Double getBboxY() {
        return this.bboxY;
    }

    public Bbox bboxY(Double bboxY) {
        this.setBboxY(bboxY);
        return this;
    }

    public void setBboxY(Double bboxY) {
        this.bboxY = bboxY;
    }

    public Double getBboxWidth() {
        return this.bboxWidth;
    }

    public Bbox bboxWidth(Double bboxWidth) {
        this.setBboxWidth(bboxWidth);
        return this;
    }

    public void setBboxWidth(Double bboxWidth) {
        this.bboxWidth = bboxWidth;
    }

    public Double getBboxHeght() {
        return this.bboxHeght;
    }

    public Bbox bboxHeght(Double bboxHeght) {
        this.setBboxHeght(bboxHeght);
        return this;
    }

    public void setBboxHeght(Double bboxHeght) {
        this.bboxHeght = bboxHeght;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bbox)) {
            return false;
        }
        return id != null && id.equals(((Bbox) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bbox{" +
            "id=" + getId() +
            ", bboxX=" + getBboxX() +
            ", bboxY=" + getBboxY() +
            ", bboxWidth=" + getBboxWidth() +
            ", bboxHeght=" + getBboxHeght() +
            "}";
    }
}
