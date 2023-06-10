package dy.whatsong.domain.music.entity;

import dy.whatsong.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicRoomMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long musicRoomMemberSeq;

	@ManyToOne
	@JoinColumn(name = "memberSeq")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "music_room_seq")
	private MusicRoom musicRoom;

	private Long ownerSeq;
}
