package uz.yshub.makesense.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.yshub.makesense.domain.Catalog;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogDTO {

    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, length = 70)
    private String name;

    private Long parentId;

    public CatalogDTO(Catalog catalog) {
        this.id = catalog.getId();
        this.name = catalog.getName();
        this.parentId = catalog.getParent() != null ? catalog.getParent().getId() : null;
    }
}
