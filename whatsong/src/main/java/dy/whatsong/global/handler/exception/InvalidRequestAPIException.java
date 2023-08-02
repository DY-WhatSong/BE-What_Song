package dy.whatsong.global.handler.exception;

public class InvalidRequestAPIException extends RuntimeException{
    public InvalidRequestAPIException(String msg){
        super(msg);
    }
}
