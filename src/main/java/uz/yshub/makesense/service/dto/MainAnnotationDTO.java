package uz.yshub.makesense.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.yshub.makesense.domain.Bbox;
import uz.yshub.makesense.domain.Category;
import uz.yshub.makesense.domain.Image;
import uz.yshub.makesense.domain.Project;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainAnnotationDTO {

    private Long id;

    private Boolean iscrowd;

    private Long image_id;

    private int category_id;

    private List<List<Double>> segmentation;

    private List<Double> bbox;

    private Double area;
}
