package dy.whatsong.domain.member.api.oauth;

import dy.whatsong.domain.member.service.oauth.OauthCodeReq;
import dy.whatsong.domain.member.service.oauth.OauthService;
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
    public void getCallbackCode(@RequestBody OauthCodeReq oauthCodeReq) {
        oauthService.getTokenInfoByResourceServer(oauthCodeReq.code());
    }
}
