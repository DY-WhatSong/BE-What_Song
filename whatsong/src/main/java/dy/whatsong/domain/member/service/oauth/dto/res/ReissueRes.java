package dy.whatsong.domain.member.service.oauth.dto.res;

import java.util.Optional;

public record ReissueRes(String access_token, Optional<String> refresh_token, Integer expires_in,
                         Optional<Integer> refresh_token_expires_in) {
}
