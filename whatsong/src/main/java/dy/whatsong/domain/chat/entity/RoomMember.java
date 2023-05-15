package dy.whatsong.domain.chat.entity;

import dy.whatsong.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roomMemberSeq;

	@ManyToOne
	@JoinColumn(name = "memberSeq")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "roomSeq")
	private ChatRoom chatRoom;
}
