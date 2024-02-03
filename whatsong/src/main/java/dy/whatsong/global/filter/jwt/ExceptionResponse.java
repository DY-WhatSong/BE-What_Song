package dy.whatsong.global.filter.jwt;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ExceptionResponse {

    private int code;
    private String message;
    private String errorDetails;
    private LocalDateTime responseTime = LocalDateTime.now();

    public ExceptionResponse() {
    }

    public ExceptionResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionResponse(int code, String message, String errorDetails) {
        this.code = code;
        this.message = message;
        this.errorDetails = errorDetails;
    }

}
