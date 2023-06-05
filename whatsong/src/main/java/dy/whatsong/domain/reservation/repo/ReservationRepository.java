package dy.whatsong.domain.reservation.repo;

import dy.whatsong.domain.reservation.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation,String> {
	Optional<List<Reservation>> findByRoomSeq(Long roomseq);
}
