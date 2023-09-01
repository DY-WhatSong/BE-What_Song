package dy.whatsong.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberRequestCacheDTO {

    @Getter
    public static class PutOrModify{
        private String roomCode;
        private Long memberSeq;
    }

    @Getter
    public static class OnlyUseRoomCode{
        private String roomCode;
    }
}
