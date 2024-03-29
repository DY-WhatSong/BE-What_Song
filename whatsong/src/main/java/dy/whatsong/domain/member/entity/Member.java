package dy.whatsong.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.dto.MemberResponseDto;
import dy.whatsong.domain.music.entity.MusicRoomMember;
import dy.whatsong.domain.profile.entity.GuestBook;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member {
    @Id
    @GeneratedValue
    private Long memberSeq;

    private String email;

    private String nickname;

    private String innerNickname;

    private String imgURL;

    private String oauthId;

    @Column(length = 500)
    private String refreshToken;

    private String profileMusic;
    // 엔티티 추가 내용
//	private String socialType;
//	private String socialId;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @OneToMany(mappedBy = "ownerMember")
    @JsonManagedReference
    private List<GuestBook> guestBooks;

    @OneToMany(mappedBy = "member")
    @JsonManagedReference
    private List<MusicRoomMember> musicRoomMembers;

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public MemberResponseDto.CheckResponse toDTO() {
        return MemberResponseDto.CheckResponse.builder()
                .memberSeq(memberSeq)
                .oauthId(oauthId)
                .socialType(socialType)
                .imgURL(imgURL)
                .email(email)
                .nickname(nickname)
                .innerNickname(innerNickname)
                .build();
    }

    public MemberDto.MemberStomp toStompDTO() {
        return MemberDto.MemberStomp.builder()
                .email(email)
                .imgURL(imgURL)
                .nickname(nickname)
                .memberSeq(memberSeq)
                .build();
    }

    public Member(String email, String nickname, String innerNickname, String imgURL, String oauthId, String refreshToken) {
        this.email = email;
        this.nickname = nickname;
        this.innerNickname = innerNickname;
        this.imgURL = imgURL;
        this.oauthId = oauthId;
        this.refreshToken = refreshToken;
        this.profileMusic = profileMusic;
    }
}
