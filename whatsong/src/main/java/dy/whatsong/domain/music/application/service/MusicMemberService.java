package dy.whatsong.domain.music.application.service;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.entity.MusicRoom;

public interface MusicMemberService {
	void createdRoomDetails(MusicRoom musicRoom, Member member);
	void deletedRoomDetails(MusicRoom musicRoom);

	boolean memberIsRoomOwner(Long memberSeq,Long roomSeq);
}
