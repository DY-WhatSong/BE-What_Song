package dy.whatsong.domain.story.entity;

import dy.whatsong.domain.member.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash(timeToLive = 86400)
@Getter
@Builder
public class Story {

    @Id
    private String id;
    private MemberDto.MemberStory memberStory;
    private LocalDateTime localDateTime;
}
