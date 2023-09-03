package dy.whatsong.domain.member.service;

import dy.whatsong.domain.member.dto.KakaoProfile;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.dto.TokenInfo;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.entity.MemberRole;
import dy.whatsong.domain.member.entity.SocialType;
import dy.whatsong.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    public Member saveMember(KakaoProfile.UsersInfo usersInfo) {
        Member member = convertKakaoProfileToMember(usersInfo);
        return memberRepository.save(member);
    }

    public ResponseEntity<?> saveMember(MemberDto.MemberJoinReqDto memberJoinReqDto) {
        Member member = memberRepository.save(convertMemberJoinReqDtoToMember(memberJoinReqDto));
        return tokenService.getTokensResponse(member);
    }

    public int updateRefreshToken(TokenInfo decodedTokenInfo) {
        return memberRepository.updateRefreshToken(decodedTokenInfo.getOauthId(), decodedTokenInfo.getEmail(), "");
    }

    public Member getMember(String oauthId) {
        return memberRepository.findByOauthId(oauthId).orElse(null);
    }

    public MemberDto.MemberResponseDto getMember(String oauthId, String email) {
        Member member = memberRepository.findByOauthIdAndEmail(oauthId, email).orElse(new Member());
        return convertToMemberResponseDto(member);
    }

    public boolean isValid(String email, SocialType socialType) {
        return memberRepository.findByEmailAndSocialType(email, socialType).isPresent();
    }

    public boolean findRefreshToken(String oauthId, String email) {
        return memberRepository.findRefreshTokenByOauthIdAndEmail(oauthId, email).isPresent();
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

    private static MemberDto.MemberResponseDto convertToMemberResponseDto(Member member) {
        return MemberDto.MemberResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .innerNickname(member.getInnerNickname())
                .imgURL(member.getImgURL())
                .oauthId(member.getOauthId())
                .refreshToken(member.getRefreshToken())
                .profileMusic(member.getProfileMusic())
                .memberRole(member.getMemberRole())
                .socialType(member.getSocialType())
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