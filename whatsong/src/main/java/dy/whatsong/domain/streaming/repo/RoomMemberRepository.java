package dy.whatsong.domain.streaming.repo;

import dy.whatsong.domain.streaming.entity.redis.RoomMember;
import org.springframework.data.repository.CrudRepository;

public interface RoomMemberRepository extends CrudRepository<RoomMember,String> {
}
