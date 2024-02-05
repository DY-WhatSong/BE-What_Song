package dy.whatsong.domain.member.api.oauth;

import dy.whatsong.domain.member.service.oauth.OauthService;
import dy.whatsong.domain.member.service.oauth.dto.OauthCodeReq;
import dy.whatsong.domain.member.service.oauth.dto.OauthCodeRes;
import dy.whatsong.domain.member.service.oauth.dto.OauthSingUpReq;
import dy.whatsong.global.dto.ResponseEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {

    private final OauthService oauthService;

    @PostMapping("/callback")
    public ResponseEnvelope<OauthCodeRes> getCallbackCode(@RequestBody OauthCodeReq oauthCodeReq) {
        OauthCodeRes oauthCodeRes = oauthService.getTokenInfoByResourceServer(oauthCodeReq.code());

        return ResponseEnvelope.of(oauthCodeRes);
    }

    @PostMapping("/signup")
    public ResponseEnvelope<Long> signUpByToken(@RequestBody OauthSingUpReq oauthSingUpReq) {
        Long memberId = oauthService.singUp(oauthSingUpReq.accessToken(), oauthSingUpReq.refreshToken(), oauthSingUpReq.innerNickName());

        return ResponseEnvelope.of(memberId);
    }
}
