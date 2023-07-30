package dy.whatsong.domain.member.api;

import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.service.MemberService;
import dy.whatsong.domain.member.service.TokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @ApiOperation(
            value = "닉네임 중복 여부 확인",
            notes = "입력한 닉네임이 회원 DB에 존재하는지 체크합니다."
    )
    @GetMapping("/user/kakao/callback22")
    public Boolean check(String nickname) {
        return false;
    }

    @ApiOperation(
            value = "회원가입 - 카카오 계정 정보 + 닉네임",
            notes = "카카오 계정 정보와 고객이 입력한 닉네임 정보로 회원 가입을 합니다."
    )
    @PostMapping("/user/login")
    public ResponseEntity login(@RequestBody MemberDto.MemberJoinReqDto memberJoinReqDto) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        memberService.saveMember(memberJoinReqDto);
        System.out.println("MemberJoinReqDto : " + memberJoinReqDto);

// Res 수정해야함. Json 리턴에 맞게!
        Member member = memberService.getMember(memberJoinReqDto.getOauthId());
//        Member member = memberService.existsByEmail(usersInfo.getKakao_account().getEmail());
        log.info("Member : {}", member);

        if(member != null) {
            return tokenService.getTokensResponse(member);
        } else {
            // 3.2. 회원 정보 DB 에 존재하지 않으면? 성공 유무 flag + 사용자 정보
            return ResponseEntity.internalServerError().build();
        }
    }

    @ApiOperation(
            value = "로그아웃"
    )
    @PostMapping("/user/logout")
    public String logout(@Valid MemberDto.LogoutRequestDto logoutRequestDto) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        memberService.updateRefreshToken(logoutRequestDto);

        return "redirect:/";
    }

    @ApiOperation(
            value = "헬스체크"
    )
    @PostMapping("/health/check")
    public ResponseEntity healthCheck() {

        log.info("HELLO");
        return ResponseEntity.ok()
                .build();
    }
}
