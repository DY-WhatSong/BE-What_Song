package dy.whatsong.domain.member.application.impl.check;

import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.repository.MemberRepository;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import dy.whatsong.global.constant.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@EssentialServiceLayer
@RequiredArgsConstructor
@Log4j2
public class MemberCheckServiceImpl implements MemberCheckService {

	private final MemberRepository memberRepository;
	private final Properties.JwtProperties jwtProperties;

	@Override
	public Member getInfoByMemberSeq(Long memberSeq) {
		System.out.println("memberSe="+memberSeq);
		return memberRepository.findById(memberSeq).orElseThrow(()->new IllegalArgumentException("회원 찾을수 없음"));
	}

	@Override
	public Member getInfoByMemberRefreshToken(String refreshToken) {
		refreshToken = refreshToken.replace(jwtProperties.getTOKEN_PREFIX(), "");
		log.info("getInfoByMemberRefreshToken-refreshToken : {}", refreshToken);
		return memberRepository.findByRefreshToken(refreshToken).get();
	}

	@Override
	public Member getInfoByMemberEmail(String email) {
		return memberRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("이메일 존재하지 않음"));
	}
}
