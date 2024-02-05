package dy.whatsong.domain.member.service.oauth;

public record OauthCodeRes(String access_token, String refresh_token, int expires_in, int refresh_token_expires_in) {
}
