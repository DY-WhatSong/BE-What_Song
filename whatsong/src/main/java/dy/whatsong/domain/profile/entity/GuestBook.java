package dy.whatsong.domain.profile.entity;

import dy.whatsong.domain.member.entity.Member;

import javax.persistence.*;

@Entity
public class GuestBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long guestBookSeq;

	private String contents;

	@ManyToOne
	private Member ownerMember;

	private Long guestMemberSeq;
}
