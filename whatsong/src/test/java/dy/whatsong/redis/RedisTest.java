/*
package dy.whatsong.redis;

import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


@SpringBootTest
public class RedisTest {

    @Autowired
    private MemberCheckService memberCheckService;

    private final RedisTemplate<String, String> redisTemplate=new RedisTemplate<>();

    @Test
    void redisStringSerializeTest(){
        //given
        long target = 3L;
        String targetNickname="테스트유저";

        //when
        Member infoByMemberSeq = memberCheckService.getInfoByMemberSeq(target);
        String nickname = infoByMemberSeq.getNickname();

        ValueOperations<String, String> op = redisTemplate.opsForValue();
        op.set(nickname,"test");

        //then
        Assertions.assertThat(op.get(targetNickname)).isEqualTo("test");
    }
}
*/
