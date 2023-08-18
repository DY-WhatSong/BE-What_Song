package dy.whatsong.domain.reservation.api;

import dy.whatsong.domain.reservation.application.service.ReservationService;
import dy.whatsong.domain.reservation.dto.ReservationDTO;
import dy.whatsong.global.annotation.EssentialController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@EssentialController
@RequiredArgsConstructor
public class ReservationAPI {

	private final ReservationService reservationService;

	@PostMapping("/reservation")
	public ResponseEntity<?> getRegistrationReservation(@RequestBody ReservationDTO.Select selectDTO){
		return reservationService.reservationMusic(selectDTO);
	}

	@GetMapping("/reservation")
	public ResponseEntity<?> getInfoReservationList(@RequestParam("roomSeq") Long roomSeq){
		return reservationService.reservationList(roomSeq);
	}

	@PostMapping("/reservation/approve")
	public ResponseEntity<?> approveToMusic(@RequestBody ReservationDTO.Approve approveDTO){
		return reservationService.approveReservation(approveDTO);
	}
}
