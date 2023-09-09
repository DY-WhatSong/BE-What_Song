package dy.whatsong.domain.member.api.cache;

import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.member.dto.MemberRequestCacheDTO;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.global.annotation.EssentialController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@EssentialController
@RequiredArgsConstructor
public class MemberCacheAPI {

    private final MemberCacheService memberCacheService;

    @PostMapping("/cache/member/put")
    public ResponseEntity<?> memberEnterTheMusicRoom(@RequestBody MemberRequestCacheDTO.PutOrModify req){
        return new ResponseEntity<>(memberCacheService.putMemberInCacheIfEmpty(
                            req.getRoomCode()
                            ,req.getMemberSeq()
        )
                ,HttpStatus.OK);
    }

    @GetMapping("/cache/member/list")
    public ResponseEntity<?> getCacheMember(@RequestBody MemberRequestCacheDTO.OnlyUseRoomCode onlyUseRoomCode){
        return new ResponseEntity<>(memberCacheService.getRoomOfMemberList(onlyUseRoomCode.getRoomCode())
                , HttpStatus.OK);
    }

    @PostMapping("/cache/member/modify")
    public ResponseEntity<?> memberLeaveTheMusicRoom(@RequestBody MemberRequestCacheDTO.PutOrModify req){
        return new ResponseEntity<>(memberCacheService.leaveMemberInCache(
                            req.getRoomCode(),
                            req.getMemberSeq()
        ),
                HttpStatus.OK);
    }
}
