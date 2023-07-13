package dy.whatsong.domain.member.repo;

import dy.whatsong.domain.member.entity.FriendsState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendsStateRepository extends JpaRepository<FriendsState,Long> {
	Optional<List<FriendsState>> findByOwnerSeq(Long ownerSeq);
}
