package dy.whatsong.domain.member.service.oauth.dto.req;

public record OauthSingUpReq(String accessToken, String refreshToken, String innerNickName) {
}
