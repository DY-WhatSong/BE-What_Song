package dy.whatsong.domain.reservation.repo;

import dy.whatsong.domain.reservation.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation,String> {
}
