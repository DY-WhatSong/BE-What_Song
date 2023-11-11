package dy.whatsong.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FriendsSearchListDTO {
    private Long memberSeq;
    private String email;
    private String nickname;
    private String imgURL;
    private boolean isAlreadyFollowing;

    public FriendsSearchListDTO(Long memberSeq, String email, String nickname, String imgURL, boolean isAlreadyFollowing) {
        this.memberSeq = memberSeq;
        this.email = email;
        this.nickname = nickname;
        this.imgURL = imgURL;
        this.isAlreadyFollowing = isAlreadyFollowing;
    }
}
