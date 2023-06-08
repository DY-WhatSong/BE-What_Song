package dy.whatsong.domain.music.dto;

import com.querydsl.core.annotations.QueryProjection;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.entity.MusicRoom;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


public class MusicRoomMemberDTO {
	private Long musicRoomMemberSeq;

	private Member member;

	private MusicRoom musicRoom;

	private Long ownerSeq;

	@QueryProjection
	public MusicRoomMemberDTO(Long musicRoomMemberSeq, Member member, MusicRoom musicRoom, Long ownerSeq) {
		this.musicRoomMemberSeq = musicRoomMemberSeq;
		this.member = member;
		this.musicRoom = musicRoom;
		this.ownerSeq = ownerSeq;
	}
}
