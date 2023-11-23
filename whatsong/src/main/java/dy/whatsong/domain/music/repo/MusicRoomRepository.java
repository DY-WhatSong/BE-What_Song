package dy.whatsong.domain.music.repo;

import dy.whatsong.domain.music.entity.MusicRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRoomRepository extends JpaRepository<MusicRoom, Long> {
    List<MusicRoom> findAllByCategory(String category);
}
