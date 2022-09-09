package uz.yshub.makesense.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO {

    private boolean success;
    private String message;
    private Object images;

    public ApiResponseDTO(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public ApiResponseDTO(boolean success, Object object) {
        this.success = success;
        this.images = object;
    }
}
