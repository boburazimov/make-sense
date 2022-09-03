package uz.yshub.makesense.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private boolean success;
    private String message;
    private Object object;

    public MessageResponse(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public MessageResponse(boolean success, Object object) {
        this.success = success;
        this.object = object;
    }
}
