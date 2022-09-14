package uz.yshub.makesense.controller.errors;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrRespModel {

    private HttpStatus status;

    private String cause;

    private String entityName;

    private String errorKey;

    private LocalDateTime timestamp;
}
