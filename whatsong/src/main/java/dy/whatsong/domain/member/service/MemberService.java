package dy.whatsong.domain.member.service;

import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.entity.MemberRole;
import dy.whatsong.domain.member.entity.SocialType;
import dy.whatsong.domain.member.repository.MemberRepository;
import dy.whatsong.domain.member.domain.KakaoProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

//    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private static final String ADMIN_TOKEN = "376d3362b0307ab01c9ef0642be92155";

    public Member saveMember(KakaoProfile.UsersInfo usersInfo) {
        Member member = convertKakaoProfileToMember(usersInfo);
        return memberRepository.save(member);
    }

    public Member saveMember(MemberDto.MemberJoinReqDto memberJoinReqDto) {
        Member member = convertMemberJoinReqDtoToMember(memberJoinReqDto);
        return memberRepository.save(member);
    }

    public boolean isValid(String email, SocialType socialType) {
        return memberRepository.findByEmailAndSocialType(email, socialType).isPresent();
    }

    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        log.info("authorizedCode : {}", authorizedCode);
        KakaoProfile.UsersInfo usersInfo = tokenService.getUserInfo(authorizedCode);
        String kakaoId = usersInfo.getId();
        String nickname = usersInfo.getKakao_account().getProfile().getNickname();
        String email = usersInfo.getKakao_account().getEmail();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;
/*
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Member kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자
            MemberRole role = MemberRole.USER;

            //   수정해야함.
            kakaoUser = new Member();
//            kakaoUser = new Member(nickname, encodedPassword, email, role, kakaoId);
            userRepository.save(kakaoUser);
        }*/

        log.info("Started");
        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Finished");
    }

    public Member existsByOauthId(String oauthId) {
        // 카카오 회원 정보 > 회원 번호(고유값)로 DB에 정보 존재 하는 지 판별
        log.info("oauthId : {}", oauthId);
        Optional<Member> byId = memberRepository.findByOauthId(oauthId);

        return byId.orElse(null);
    }

    public Member existsByEmail(String email) {
        // 카카오 회원 정보 > 회원 번호(고유값)로 DB에 정보 존재 하는 지 판별
        log.info("email : {}", email);
        Optional<Member> byId = memberRepository.findByEmail(email);

        return byId.orElse(null);
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
