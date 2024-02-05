package dy.whatsong.domain.member.service.oauth.dto;

public record OauthSingUpReq(String accessToken, String refreshToken, String innerNickName) {
}
