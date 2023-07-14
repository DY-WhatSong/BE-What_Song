package dy.whatsong.domain.music.entity;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(timeToLive = 60)
public class RoomInvite {
	public String shared_link;
	public String qr_link;
}
