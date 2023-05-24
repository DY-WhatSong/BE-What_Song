package dy.whatsong.domain.music.repo;

import dy.whatsong.domain.music.entity.MusicRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRoomRepository extends JpaRepository<MusicRoom,Long> {
}
