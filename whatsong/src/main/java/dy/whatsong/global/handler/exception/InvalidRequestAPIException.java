package dy.whatsong.global.handler.exception;

public class InvalidRequestAPIException extends RuntimeException{

    private final int errorCode;

    public InvalidRequestAPIException(String msg,int errorCode){
        super(msg);
        this.errorCode= errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
