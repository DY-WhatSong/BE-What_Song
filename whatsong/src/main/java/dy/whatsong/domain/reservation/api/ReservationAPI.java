package dy.whatsong.domain.reservation.api;

import dy.whatsong.domain.reservation.application.service.ReservationService;
import dy.whatsong.domain.reservation.dto.ReservationDTO;
import dy.whatsong.domain.reservation.dto.ReservationDeleteDTO;
import dy.whatsong.domain.reservation.dto.ReservationRejectDTO;
import dy.whatsong.global.annotation.EssentialController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@EssentialController
@RequiredArgsConstructor
public class ReservationAPI {

    private final ReservationService reservationService;

    @PostMapping("/reservation")
    public ResponseEntity<?> getRegistrationReservation(@RequestBody ReservationDTO.Select selectDTO) {
        return reservationService.reservationMusic(selectDTO);
    }

    @GetMapping("/reservation")
    public ResponseEntity<?> getInfoReservationList(@RequestParam("roomSeq") Long roomSeq) {
        return new ResponseEntity<>(reservationService.reservationList(roomSeq), HttpStatus.OK);
    }

    @PostMapping("/reservation/approve")
    public ResponseEntity<?> approveToMusic(@RequestBody ReservationDTO.Approve approveDTO) {
        return reservationService.approveReservation(approveDTO);
    }

    @GetMapping("/reservation/approve/list")
    public ResponseEntity<?> approveMusicList(@RequestParam("roomSeq") Long roomSeq) {
        return new ResponseEntity<>(
                reservationService.approveReservationList(roomSeq),
                HttpStatus.OK);
    }

    @DeleteMapping("/reservation")
    public ResponseEntity<?> reservationReject(@RequestBody ReservationRejectDTO reservationRejectDTO) {
        return new ResponseEntity<>(
                reservationService.reservationReject(reservationRejectDTO.getRoomSeq(), reservationRejectDTO.getReservationId()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/reservation/approve")
    public ResponseEntity<?> approveMusicDelete(@RequestBody ReservationDeleteDTO reservationDeleteDTO) {
        reservationService.reservationDelete(reservationDeleteDTO.getReservationId(), reservationDeleteDTO.getRecognize());
        return new ResponseEntity<>(reservationDeleteDTO.getReservationId() + "delete", HttpStatus.OK);
    }
}
