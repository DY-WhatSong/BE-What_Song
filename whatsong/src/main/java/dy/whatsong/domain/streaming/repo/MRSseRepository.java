package dy.whatsong.domain.streaming.repo;

import dy.whatsong.domain.streaming.entity.room.MRWS;
import org.springframework.data.repository.CrudRepository;

public interface MRSseRepository extends CrudRepository<MRWS,String> {
}
