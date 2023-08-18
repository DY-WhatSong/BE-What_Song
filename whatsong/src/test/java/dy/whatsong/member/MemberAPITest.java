/*
package dy.whatsong.member;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberAPITest {

	@Autowired MemberRepository memberRepository;

	@Test
	public void 회원_더미(){
		for (int i=0;i<35000;i++){
			memberRepository.save(
					Member.builder()
							.innerNickname("nick"+i)
							.nickname("dummy"+i)
							.build()
			);
		}
	}
}
*/
