package dy.whatsong.domain.member.service;

import dy.whatsong.domain.member.domain.KakaoProfile;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.entity.MemberRole;
import dy.whatsong.domain.member.entity.SocialType;
import dy.whatsong.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(KakaoProfile.UsersInfo usersInfo) {
        Member member = convertKakaoProfileToMember(usersInfo);
        return memberRepository.save(member);
    }

    public Member saveMember(MemberDto.MemberJoinReqDto memberJoinReqDto) {
        Member member = convertMemberJoinReqDtoToMember(memberJoinReqDto);
        return memberRepository.save(member);
    }

    public int updateRefreshToken(MemberDto.LogoutRequestDto logoutRequestDto) {
        return memberRepository.updateRefreshToken(logoutRequestDto.getOauthId(), logoutRequestDto.getEmail(), "");
    }

    public Member getMember(String oauthId) {
        return memberRepository.findByOauthId(oauthId).orElse(new Member());
    }

    public boolean isValid(String email, SocialType socialType) {
        return memberRepository.findByEmailAndSocialType(email, socialType).isPresent();
    }

    private static Member convertKakaoProfileToMember(KakaoProfile.UsersInfo usersInfo) {
        return Member.builder()
                .email(usersInfo.getKakao_account().getEmail())
                .nickname(usersInfo.getKakao_account().getProfile().getNickname())
                .innerNickname("")
                .imgURL("")
                .oauthId(usersInfo.getId())
                .refreshToken("")
                .profileMusic("")
                .memberRole(MemberRole.USER)
                .socialType(SocialType.KAKAO)
                .build();
    }

    private static Member convertMemberJoinReqDtoToMember(MemberDto.MemberJoinReqDto memberDto) {
        return Member.builder()
                .memberSeq(memberDto.getMemberSeq())
                .email(memberDto.getEmail())
                .nickname(memberDto.getNickname())
                .innerNickname(memberDto.getInnerNickname())
                .imgURL(memberDto.getImgURL())
                .oauthId(memberDto.getOauthId())
                .refreshToken(memberDto.getRefreshToken())
                .profileMusic(memberDto.getProfileMusic())
                .memberRole(memberDto.getMemberRole())
                .socialType(memberDto.getSocialType())
                .build();
    }
}
