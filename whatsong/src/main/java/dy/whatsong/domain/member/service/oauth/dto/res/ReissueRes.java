package dy.whatsong.domain.member.service.oauth.dto.res;

import java.util.Optional;

public record ReissueRes(String accessToken, Optional<String> refreshToken, Integer expires_in,
                         Optional<Integer> refresh_token_expires_in) {
}
