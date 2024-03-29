package dy.whatsong.domain.member.api.cache;

import dy.whatsong.domain.chat.model.TestDTO;
import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.member.dto.MemberRequestCacheDTO;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.global.annotation.EssentialController;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Test;
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
    public void memberEnterTheMusicRoom(@RequestBody TestDTO testDTO){
        memberCacheService.putMemberInCacheIfEmpty(
                MemberRequestCacheDTO.BasicInfo.builder()
                        .roomCode(testDTO.getRoomCode())
                        .username(testDTO.getUsername())
                .build());
    }

    @GetMapping("/cache/member/list")
    public ResponseEntity<?> getCacheMember(@RequestBody MemberRequestCacheDTO.OnlyUseRoomCode onlyUseRoomCode){
        return new ResponseEntity<>(memberCacheService.getRoomOfMemberList(onlyUseRoomCode.getRoomCode())
                , HttpStatus.OK);
    }

    @PostMapping("/cache/member/modify")
    public void memberLeaveTheMusicRoom(@RequestBody TestDTO testDTO){
        memberCacheService.leaveMemberInCache(MemberRequestCacheDTO.BasicInfo.builder()
                .roomCode(testDTO.getRoomCode())
                .username(testDTO.getUsername())
                .build());
    }
}
