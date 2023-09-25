package dy.whatsong.domain.streaming.dto;

import dy.whatsong.domain.streaming.entity.room.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MRWSResponse {

    @Getter
    @Builder
    public static class updateInfoRes{
        private String videoId;
        private Status status;
        private String timeStamp;
        private String username;
    }
}
