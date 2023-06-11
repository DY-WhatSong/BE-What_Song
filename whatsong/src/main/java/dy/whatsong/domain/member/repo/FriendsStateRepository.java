package dy.whatsong.domain.member.repo;

import dy.whatsong.domain.member.entity.FriendsState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendsStateRepository extends JpaRepository<FriendsState,Long> {
	Optional<FriendsState> findByOwnerSeq(Long ownerSeq);
}
