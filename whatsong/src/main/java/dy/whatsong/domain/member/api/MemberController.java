package dy.whatsong.domain.member.api;

import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.dto.TokenInfo;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.service.MemberService;
import dy.whatsong.domain.member.service.TokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @ApiOperation(
            value = "맴버 정보 조회"
    )
    @GetMapping(value = "/members/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMemberInfo(@RequestHeader("Authorization") String accessToken,
                                           HttpServletRequest request) {
        log.info("getMemberInfo.accessToken : {}", accessToken);
        TokenInfo decodedTokenInfo = TokenInfo.builder()
                .oauthId(request.getAttribute("oauthId").toString())
                .email(request.getAttribute("email").toString())
                .build();
        MemberDto.MemberResponseDto member = memberService.getMember(decodedTokenInfo.getOauthId(), decodedTokenInfo.getEmail());

        return ResponseEntity.ok().body(member);
    }

}
