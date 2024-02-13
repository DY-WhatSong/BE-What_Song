/*
package dy.whatsong.domain.member.api;

import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.dto.KakaoProfile;
import dy.whatsong.domain.member.dto.OAuthToken;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class TokenController {

    private final TokenService tokenService;
    private final MemberService memberService;
    private final MemberCheckService memberCheckService;

    // 프론트에서 인가 코드 받는 API
    // 인가 코드로 엑세스 토큰 발급 -> 사용자 정보 조회 -> DB 저장 -> jwt 토큰 발급 -> 프론트에 토큰 전달
    @ApiOperation(
            value = "회원가입 - 카카오 계정 정보 + 닉네임",
            notes = "카카오 계정 정보와 고객이 입력한 닉네임 정보로 회원 가입을 합니다."
    )
    @GetMapping(value = "/kakao/callback")
    public ResponseEntity kakaoLogin(@RequestParam String code) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        log.info("KAKAO/callback");

        // 1. 넘어온 인가 코드를 통해 access_token 발급
        OAuthToken oauthToken = tokenService.getAccessToken(code);

        // 2. 액세스 토큰 -> 카카오 사용자 정보
        KakaoProfile.UsersInfo usersInfo = tokenService.getUserInfoByToken(oauthToken.getAccess_token());

        // 3. 해당 카카오 사용자 정보를 통해 DB에 존재하는지 판별
        // Res 수정해야함. Json 리턴에 맞게!
        Member member = memberService.getMember(usersInfo.getId());
//        Member member = memberService.existsByEmail(usersInfo.getKakao_account().getEmail());

        if (member != null) {
            // 3.1. 회원 정보 DB 에 존재하면? 토큰받기
            return tokenService.getTokensResponse(member);
        } else {
            // 3.2. 회원 정보 DB 에 존재하지 않으면? 성공 유무 flag + 사용자 정보
            return tokenService.getKakaoProfileResponse(usersInfo);
        }
    }

    @GetMapping(value = "/token/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request) {
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return tokenService.getReissusedTokensResponse(request);
    }

    @GetMapping("/member/info/refresh")
    public ResponseEntity<?> getMemberInfoByRefreshToken(@RequestHeader(name = "refreshToken") String refreshToken) {
        return new ResponseEntity(
                memberCheckService.getInfoByMemberRefreshToken(refreshToken).toDTO(),
                HttpStatus.OK
        );
    }
}
*/
