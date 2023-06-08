package dy.whatsong.domain.music.repo;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.music.entity.MusicRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MusicMemberRepository extends JpaRepository<MusicRoomMember,Long> {
	Optional<List<MusicRoomMember>> findByMember(Member member);
	Optional<MusicRoomMember> findByMusicRoom(MusicRoom musicRoom);
}
