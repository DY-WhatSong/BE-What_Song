package dy.whatsong.domain.member.api;

import dy.whatsong.domain.member.dto.TokenInfo;
import dy.whatsong.domain.member.service.MemberService;
import dy.whatsong.domain.member.service.TokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @ApiOperation(
            value = "로그아웃"
    )
    @GetMapping("/user/logout")
    public void logout(@RequestHeader("Refresh") String refreshToken) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        TokenInfo decodedTokenInfo = tokenService.getTokenInfoFromToken(refreshToken);
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
