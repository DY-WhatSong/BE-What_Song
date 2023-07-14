package dy.whatsong.global.filter.jwt;

import java.time.LocalDateTime;

public class ExceptionResponse {

    private int code;
    private String message;
    private String errorDetails;
    private LocalDateTime responseTime = LocalDateTime.now();

    public ExceptionResponse() {
    }

    public ExceptionResponse(int code, String message, String errorDetails) {
        this.code = code;
        this.message = message;
        this.errorDetails = errorDetails;
    }
}
