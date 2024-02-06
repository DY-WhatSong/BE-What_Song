package dy.whatsong.domain.member.api.oauth;

import dy.whatsong.domain.member.service.oauth.OauthService;
import dy.whatsong.domain.member.service.oauth.dto.req.OauthCodeReq;
import dy.whatsong.domain.member.service.oauth.dto.req.OauthSingUpReq;
import dy.whatsong.domain.member.service.oauth.dto.res.MemberDetailRes;
import dy.whatsong.domain.member.service.oauth.dto.res.OauthCodeRes;
import dy.whatsong.global.dto.ResponseEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEnvelope<String> signUpByToken(@RequestBody OauthSingUpReq oauthSingUpReq) {
        String oauthId = oauthService.singUp(oauthSingUpReq.accessToken(), oauthSingUpReq.refreshToken(), oauthSingUpReq.innerNickName());

        return ResponseEnvelope.of(oauthId);
    }

    @GetMapping("/me")
    public ResponseEnvelope<MemberDetailRes> getMe(@RequestHeader("Authorization") String accessToken) {
        MemberDetailRes memberDetailRes = oauthService.getMe(accessToken);

        return ResponseEnvelope.of(memberDetailRes);
    }
}
