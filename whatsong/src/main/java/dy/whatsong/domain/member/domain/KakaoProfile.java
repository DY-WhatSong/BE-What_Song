package dy.whatsong.domain.member.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class KakaoProfile {

    Long id;
    String email;
    String nickname;
    String imgUrl;

}
