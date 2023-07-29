package dy.whatsong.domain.streaming.entity.room;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@RedisHash(timeToLive = 60 * 60 * 24)
public class MRSse {
    @Id
    private String roomCode;
    private String timestamp;
    private Status status;
    private String videoId;

}
