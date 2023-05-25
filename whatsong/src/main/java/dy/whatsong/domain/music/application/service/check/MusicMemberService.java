package dy.whatsong.domain.music.application.service.check;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.entity.MusicRoom;

public interface MusicMemberService {
	void createdRoomDetails(MusicRoom musicRoom, Member member);
}
