package dy.whatsong.domain.reservation.application.service;

import dy.whatsong.domain.reservation.dto.ReservationDTO;
import dy.whatsong.domain.youtube.dto.VideoDTO;
import org.springframework.http.ResponseEntity;

public interface ReservationService {
	ResponseEntity<?> reservationMusic(ReservationDTO.Select selectDTO);

	ResponseEntity<?> reservationList(Long roomSeq);

}
