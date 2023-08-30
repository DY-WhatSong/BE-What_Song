package dy.whatsong.domain.member.application.service.cache;


import dy.whatsong.domain.member.dto.MemberResponseDto;
import dy.whatsong.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberCacheService {
    ResponseEntity<?> putMemberInCacheIfEmpty(String roomCode,Long memberSeq);

    List<MemberResponseDto.CheckResponse> getRoomOfMemberList(String roomCode);

    void leaveMemberInCache(String roomCode,Long memberSeq);
}
