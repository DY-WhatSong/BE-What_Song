package dy.whatsong.global.handler.exception;

public class ErrorResponse {
    private int errorCode;
    private String errorMsg;

    public ErrorResponse(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
