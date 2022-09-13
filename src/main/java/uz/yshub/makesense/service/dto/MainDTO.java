package uz.yshub.makesense.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the Main request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainDTO implements Serializable {

    @NotNull
    MainProjectDTO info;

    @NotNull
    List<MainImageDTO> images;

    @NotNull
    List<MainAnnotationDTO> annotations;

    @NotNull
    List<CategoryDTO> categories;
}
