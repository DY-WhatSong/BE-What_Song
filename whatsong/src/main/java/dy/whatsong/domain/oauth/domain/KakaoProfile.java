package dy.whatsong.domain.oauth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KakaoProfile {

    Long id;
    String email;
    String nickname;

}
