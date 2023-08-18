package dy.whatsong.domain.streaming.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSseResponse {
    private Long memberSeq;
    private String username;
}
