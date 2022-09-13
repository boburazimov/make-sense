package uz.yshub.makesense.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.yshub.makesense.domain.Project;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link Project} entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainProjectDTO implements Serializable {

    @NotNull
    private String description;
}
