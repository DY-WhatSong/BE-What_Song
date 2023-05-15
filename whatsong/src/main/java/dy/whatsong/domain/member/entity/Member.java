package dy.whatsong.domain.member.entity;

import lombok.*;

import javax.persistence.*;

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

	private String imgURL;

	private String oauthId;

	private String refreshToken;

	@Enumerated(EnumType.STRING)
	private MemberRole memberRole;

	@Enumerated(EnumType.STRING)
	private SocialType socialType;
}
