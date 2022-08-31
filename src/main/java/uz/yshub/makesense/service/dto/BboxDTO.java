package uz.yshub.makesense.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.yshub.makesense.domain.Bbox} entity.
 */
public class BboxDTO implements Serializable {

    private Long id;

    private Double bboxX;

    private Double bboxY;

    private Double bboxWidth;

    private Double bboxHeght;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBboxX() {
        return bboxX;
    }

    public void setBboxX(Double bboxX) {
        this.bboxX = bboxX;
    }

    public Double getBboxY() {
        return bboxY;
    }

    public void setBboxY(Double bboxY) {
        this.bboxY = bboxY;
    }

    public Double getBboxWidth() {
        return bboxWidth;
    }

    public void setBboxWidth(Double bboxWidth) {
        this.bboxWidth = bboxWidth;
    }

    public Double getBboxHeght() {
        return bboxHeght;
    }

    public void setBboxHeght(Double bboxHeght) {
        this.bboxHeght = bboxHeght;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BboxDTO)) {
            return false;
        }

        BboxDTO bboxDTO = (BboxDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bboxDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BboxDTO{" +
            "id=" + getId() +
            ", bboxX=" + getBboxX() +
            ", bboxY=" + getBboxY() +
            ", bboxWidth=" + getBboxWidth() +
            ", bboxHeght=" + getBboxHeght() +
            "}";
    }
}
