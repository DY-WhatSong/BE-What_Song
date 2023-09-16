package dy.whatsong.domain.streaming.entity.redis;

import dy.whatsong.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash(value = "roomMember",timeToLive = 86400)
@Getter
@Builder
@Cacheable(value="defaultCache", key="#pk", unless="#result == null")
public class RoomMember {

    @Id
    private String roomCode;

    private List<Member> memberList;

    private List<String> ttlTimes;
}
