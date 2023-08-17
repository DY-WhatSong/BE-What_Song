package dy.whatsong.domain.member.application.service.check;

import dy.whatsong.domain.member.entity.Member;

public interface MemberCheckService {
	Member getInfoByMemberSeq(Long memberSeq);

	Member getInfoByMemberRefreshToken(String refershToken);
}
