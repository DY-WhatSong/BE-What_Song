package dy.whatsong.domain.streaming.dto;

import dy.whatsong.domain.streaming.entity.room.Status;
import lombok.Getter;

@Getter
public class MRSseRequest {

    @Getter
    public static class OnlyUseTimeStamp{
        private String timestamp;

    }
    @Getter
    public static class OnlyUseStatus{
        private Status status;
    }
}
