package dy.whatsong.domain.member.api;

import dy.whatsong.domain.member.domain.KakaoProfile;
import dy.whatsong.domain.member.domain.OAuthToken;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.service.MemberService;
import dy.whatsong.domain.member.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/oauth")
@Slf4j
public class TokenController {

    private final TokenService tokenService;
    private final MemberService memberService;

    // 프론트에서 인가 코드 받는 API
    // 인가 코드로 엑세스 토큰 발급 -> 사용자 정보 조회 -> DB 저장 -> jwt 토큰 발급 -> 프론트에 토큰 전달
//    @GetMapping("/token")
    @GetMapping(value = "/user/kakao/callback")
    public ResponseEntity kakaoLogin(@RequestParam String code) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        log.info("authorizedCode : {}", code);

        // 1. 넘어온 인가 코드를 통해 access_token 발급
        OAuthToken oauthToken = tokenService.getAccessToken(code);
        log.info("OAuthToken : {}", oauthToken);

        // 2. 액세스 토큰 -> 카카오 사용자 정보
        KakaoProfile.UsersInfo usersInfo = tokenService.getUserInfoByToken(oauthToken.getAccess_token());
        log.info("KakaoProfile : {}", usersInfo);

        // 3. 해당 카카오 사용자 정보를 통해 DB에 존재하는지 판별
        // Res 수정해야함. Json 리턴에 맞게!
        Member member = memberService.existsByOauthId(usersInfo.getId());
//        Member member = memberService.existsByEmail(usersInfo.getKakao_account().getEmail());
        log.info("Member : {}", member);

        if(member != null) {
            // 3.1. 회원 정보 DB 에 존재하면? 토큰받기
            return tokenService.getTokensResponse(member);
        } else {
            // 3.2. 회원 정보 DB 에 존재하지 않으면? 성공 유무 flag + 사용자 정보
            return tokenService.getKakaoProfileResponse(usersInfo);
        }
    }

    @GetMapping(value = "/user/token/reissue")
    public ResponseEntity<?> reissue(@RequestHeader("refresh") String refreshToken) {
        log.info("refreshToken : {}", refreshToken);
        return new ResponseEntity<>(tokenService.getReissusedTokensResponse(refreshToken), HttpStatus.OK);
    }

    @GetMapping(value = "/test/reissue")
    public ResponseEntity<?> testReissue(@RequestHeader("refresh") String refreshToken) {
        log.info("refreshToken : {}", refreshToken);
        return new ResponseEntity<>(tokenService.getReissusedTokensResponse(refreshToken), HttpStatus.OK);
    }

    @GetMapping(value = "/api/test")
    public String test() {
        return "";
    }

}
