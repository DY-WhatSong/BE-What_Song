package dy.whatsong.domain.member.service.oauth.dto.res;

public record OauthCodeRes(String access_token, String refresh_token, int expires_in, int refresh_token_expires_in,
                           String oauthId) {

    public OauthCodeRes(OauthCodeRes oauthCodeRes, String oauthId) {
        this(oauthCodeRes.access_token, oauthCodeRes.refresh_token, oauthCodeRes.expires_in, oauthCodeRes.refresh_token_expires_in, oauthId);
    }
}
