package dy.whatsong.domain.member.dto;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.entity.MemberRole;
import dy.whatsong.domain.member.entity.SocialType;
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

    public FriendsSearchListDTO(Member member,boolean isAlreadyFollowing) {
        this.memberSeq = member.getMemberSeq();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.imgURL = member.getImgURL();
        this.isAlreadyFollowing = isAlreadyFollowing;
    }
}
