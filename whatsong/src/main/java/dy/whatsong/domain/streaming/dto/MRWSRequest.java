package dy.whatsong.domain.streaming.dto;

import dy.whatsong.domain.streaming.entity.room.Status;
import lombok.Getter;

@Getter
public class MRWSRequest {
    @Getter
    public static class playerCurrentState{
        private Status status;
        private String timeStamp;
    }
}
