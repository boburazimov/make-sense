package uz.yshub.makesense.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Annotation.
 */
@Entity
@Table(name = "annotation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Annotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "iscrowd")
    private Boolean iscrowd;

    @Column(name = "area")
    private Double area;

    @OneToOne
    @JoinColumn(unique = true)
    private Bbox bbox;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Image image;

    @ManyToOne
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Annotation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIscrowd() {
        return this.iscrowd;
    }

    public Annotation iscrowd(Boolean iscrowd) {
        this.setIscrowd(iscrowd);
        return this;
    }

    public void setIscrowd(Boolean iscrowd) {
        this.iscrowd = iscrowd;
    }

    public Double getArea() {
        return this.area;
    }

    public Annotation area(Double area) {
        this.setArea(area);
        return this;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Bbox getBbox() {
        return this.bbox;
    }

    public void setBbox(Bbox bbox) {
        this.bbox = bbox;
    }

    public Annotation bbox(Bbox bbox) {
        this.setBbox(bbox);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Annotation project(Project project) {
        this.setProject(project);
        return this;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Annotation image(Image image) {
        this.setImage(image);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Annotation category(Category category) {
        this.setCategory(category);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Annotation)) {
            return false;
        }
        return id != null && id.equals(((Annotation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Annotation{" +
            "id=" + getId() +
            ", iscrowd='" + getIscrowd() + "'" +
            ", area=" + getArea() +
            "}";
    }
}
