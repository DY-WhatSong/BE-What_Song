package dy.whatsong.domain.streaming.entity.room;

public enum Status {
    PLAYING,PAUSE,NONE;

    public static Boolean statusPause(Status statusType){
        return statusType.equals(Status.PAUSE);
    }
}
