package dy.whatsong.domain.member.service.oauth.dto;

public record OauthTokenValidRes(Long id, int expires_in, int app_id) {
}
