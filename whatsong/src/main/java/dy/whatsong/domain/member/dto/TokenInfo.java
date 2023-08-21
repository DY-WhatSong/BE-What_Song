package dy.whatsong.domain.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TokenInfo {

    private String email;
    private String oauthId;

}
