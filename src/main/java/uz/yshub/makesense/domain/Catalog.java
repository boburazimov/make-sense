package uz.yshub.makesense.domain;

import lombok.*;
import uz.yshub.makesense.service.dto.CatalogDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Catalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, length = 70)
    private String name;

    @ManyToOne
    private Catalog parent;

    public Catalog(String name, Catalog parent) {
        this.name = name;
        this.parent = parent;
    }
}
