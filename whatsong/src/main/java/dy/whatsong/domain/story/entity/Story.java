package dy.whatsong.domain.story.entity;

import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.story.dto.StoryVideo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash(timeToLive = 86400)
@Getter
@Builder
public class Story implements Serializable {

    @Id
    private String id;
    private MemberDto.MemberStory memberStory;
    private LocalDateTime postTime;
    private String img_url;
    private String start;
    private String end;
    private StoryVideo storyVideo;
}
