package dy.whatsong.domain.member.application.impl.check;

import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.repo.MemberRepository;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@EssentialServiceLayer
@RequiredArgsConstructor
@Log4j2
public class MemberCheckServiceImpl implements MemberCheckService {

	private final MemberRepository memberRepository;

	@Override
	public Member getInfoByMemberSeq(Long memberSeq) {
		return memberRepository.findById(memberSeq).orElseThrow(()->new IllegalArgumentException("회원 찾을수 없음"));
	}
}
