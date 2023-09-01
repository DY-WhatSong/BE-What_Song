package dy.whatsong.domain.member.api;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.dto.TokenInfo;
import dy.whatsong.domain.member.service.MemberService;
import dy.whatsong.domain.member.service.TokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @ApiOperation(
            value = "회원가입"
    )
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.MemberJoinReqDto memberJoinReqDto) {
        return memberService.saveMember(memberJoinReqDto);
    }

    @ApiOperation(
            value = "로그아웃"
    )
    @GetMapping("/user/logout")
    public void logout(HttpServletRequest request) {
        TokenInfo decodedTokenInfo = TokenInfo.builder()
                .oauthId(request.getAttribute("oauthId").toString())
                .email(request.getAttribute("email").toString())
                .build();
        log.info("getEmail : {}", decodedTokenInfo.getEmail());
        log.info("getOauthId : {}", decodedTokenInfo.getOauthId());
        memberService.updateRefreshToken(decodedTokenInfo);
    }

    @ApiOperation(
            value = "헬스체크"
    )
    @PostMapping("/health/check")
    public ResponseEntity<?> healthCheck() {

        log.info("HELLO");
        return ResponseEntity.ok()
                .build();
    }
}
