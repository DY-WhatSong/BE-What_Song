package dy.whatsong.domain.reservation.application.service;

import dy.whatsong.domain.reservation.dto.ReservationDTO;
import dy.whatsong.domain.reservation.entity.Recognize;
import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.youtube.dto.VideoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {
	ResponseEntity<?> reservationMusic(ReservationDTO.Select selectDTO);

	List<Reservation> reservationList(Long roomSeq);

	List<Reservation> approveReservationList(Long roomSeq);

	ResponseEntity<?> approveReservation(ReservationDTO.Approve approveDTO);

	ResponseEntity<?> musicSkipByController(Long roomSeq);

	List<Reservation> reservationReject(Long roomSeq,String reservationId);
}
