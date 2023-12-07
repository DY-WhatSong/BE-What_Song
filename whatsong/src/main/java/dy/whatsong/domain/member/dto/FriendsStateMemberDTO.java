package dy.whatsong.domain.member.dto;

import lombok.Getter;

@Getter
public class FriendsStateMemberDTO {
    private Long memberSeq;
    private String email;
    private String nickname;
    private String imgURL;
    private boolean isAlreadyFollowing;

    public FriendsStateMemberDTO(Long memberSeq, String email, String nickname, String imgURL, boolean isAlreadyFollowing) {
        this.memberSeq = memberSeq;
        this.email = email;
        this.nickname = nickname;
        this.imgURL = imgURL;
        this.isAlreadyFollowing = isAlreadyFollowing;
    }
}
