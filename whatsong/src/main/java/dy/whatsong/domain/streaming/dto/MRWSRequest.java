package dy.whatsong.domain.streaming.dto;

import dy.whatsong.domain.streaming.entity.room.Controller;
import dy.whatsong.domain.streaming.entity.room.Status;
import lombok.Getter;

@Getter
public class MRWSRequest {
    @Getter
    public static class playerCurrentState{
        private Controller controller;
        private Long roomSeq;
    }

    @Getter
    public static class updateInfo{
        private String videoId;
        private Status status;
        private String timeStamp;
        private String username;
    }

    @Getter
    public static class OnlyRoomSeq{
        private Long roomSeq;
    }

    @Getter
    public static class OnlyMemberSeq{
        private Long memberSeq;
    }
}
