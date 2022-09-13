package uz.yshub.makesense.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.yshub.makesense.domain.Image;

import java.io.Serializable;

/**
 * A DTO for the {@link Image} entity.
 */
@Schema(description = "Image entity.")
@Getter
@Setter
@NoArgsConstructor
public class MainImageDTO implements Serializable {

    private Long id;

    private Integer width;

    private Integer height;

    private String fileName;
}
