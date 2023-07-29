package dy.whatsong.domain.streaming.dto;

import dy.whatsong.domain.streaming.entity.MemberSseResponse;
import dy.whatsong.domain.streaming.entity.room.Status;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MRSseResponse {
    private Long roomSeq;
    private String roomCode;
    private String currentTime;
    private Status status;
    private String videoId;
    private List<MemberSseResponse> sseMemberList;
}
