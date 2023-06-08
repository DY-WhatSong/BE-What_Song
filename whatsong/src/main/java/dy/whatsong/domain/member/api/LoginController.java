package dy.whatsong.domain.member.api;

import dy.whatsong.domain.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @ApiOperation(
            value = "닉네임 중복 여부 확인",
            notes = "입력한 닉네임이 회원 DB에 존재하는지 체크합니다."
    )
    @GetMapping("/user/kakao/callback22")
    public Boolean check(String nickname) {
        return false;
    }

    @ApiOperation(
            value = "회원가입 - 카카오 계정 정보 + 닉네임",
            notes = "카카오 계정 정보와 고객이 입력한 닉네임 정보로 회원 가입을 합니다."
    )
    @GetMapping("/user/login")
    public String kakaoLogin(String code) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
//        memberService.saveMember(ne);
        System.out.println("code : " + code);

        return "redirect:/";
    }
}
