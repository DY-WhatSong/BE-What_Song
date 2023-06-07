package dy.whatsong.domain.music.application.service.check;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.entity.MusicRoomMember;

import java.util.List;

public interface MusicMemberCheckService {
	List<MusicRoomMember> getInfoMRMListByMember(Member member);
}