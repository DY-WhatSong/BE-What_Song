package dy.whatsong.domain.streaming.dto;

import dy.whatsong.domain.streaming.entity.room.Status;
import lombok.Getter;

@Getter
public class MRWSRequest {
    @Getter
    public static class playerCurrentState{
        private String videoId;
        private Status status;
        private String timeStamp;
    }

    @Getter
    public static class OnlyRoomSeq{
        private Long roomSeq;
    }

    @Getter
    public static class OnlyMemberSeq{
        private Long memberSeq;
    }

    @Getter
    public static class userEnterState{
        private Long memberSeq;
    }
}
