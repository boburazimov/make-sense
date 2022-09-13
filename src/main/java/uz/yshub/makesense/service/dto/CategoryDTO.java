package uz.yshub.makesense.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.yshub.makesense.domain.Category;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link uz.yshub.makesense.domain.Category} entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String colorCode;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.colorCode = category.getColorCode();
    }

    public CategoryDTO(String name, String description, String colorCode) {
        this.name = name;
        this.description = description;
        this.colorCode = colorCode;
    }
}
