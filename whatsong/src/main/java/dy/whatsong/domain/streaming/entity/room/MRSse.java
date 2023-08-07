package dy.whatsong.domain.streaming.entity.room;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Builder
@RedisHash(value = "mrsse",timeToLive = 60 * 60 * 24)
public class MRSse {
    @Id
    private String roomCode;
    private String timestamp;
    private Status status;
    private String videoId;

}
