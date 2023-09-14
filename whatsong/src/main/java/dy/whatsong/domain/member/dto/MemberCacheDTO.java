package dy.whatsong.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCacheDTO {

    @Getter
    @Builder
    public static class BasicInfo{
        private String roomCode;
        private String username;
    }
}
