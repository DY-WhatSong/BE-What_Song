package dy.whatsong.domain.member.entity;

import dy.whatsong.domain.chat.entity.ChatRoomMember;
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
public class Member {
	@Id
	@GeneratedValue
	private Long memberSeq;

	private String email;

	private String nickname;

	private String innerNickname;

	private String imgURL;

	private String oauthId;

	private String refreshToken;

	private String profileMusic;

	@Enumerated(EnumType.STRING)
	private MemberRole memberRole;

	@Enumerated(EnumType.STRING)
	private SocialType socialType;

	@OneToMany(mappedBy = "member")
	private List<ChatRoomMember> chatMembers;

	@OneToMany(mappedBy = "ownerMember")
	private List<GuestBook> guestBooks;

	@OneToMany(mappedBy = "member")
	private List<MusicRoomMember> musicRoomMembers;
}
