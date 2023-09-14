package dy.whatsong.domain.member.application.service.cache;


import dy.whatsong.domain.member.dto.MemberResponseDto;
import dy.whatsong.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberCacheService {
    /**
        실제 DB에 저장되는 username은 KAKAO OAuth callback값인 카카오톡 이름값이지만,
        STOMP에선 고유 값 보장을 위해 이메일을 이용하도록합니다.
    */
    List<MemberResponseDto.CheckResponse> putMemberInCacheIfEmpty(String roomCode,String username);

    List<MemberResponseDto.CheckResponse> getRoomOfMemberList(String roomCode);

    List<MemberResponseDto.CheckResponse> leaveMemberInCache(String roomCode,Long memberSeq);

    Integer getUserCountInRoom(String roomCode);

    Boolean memberIfExistEnter(Long memberSeq,String roomCode);
}
