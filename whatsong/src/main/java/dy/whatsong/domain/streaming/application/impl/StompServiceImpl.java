package dy.whatsong.domain.streaming.application.impl;

import dy.whatsong.domain.reservation.application.service.ReservationService;
import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.reservation.repo.ReservationRepository;
import dy.whatsong.domain.streaming.application.service.StompService;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@EssentialServiceLayer
public class StompServiceImpl implements StompService {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @Override
    public List<Reservation> musicSkipInPlaylist(List<Reservation> reservationList, Long roomSeq) {
        if (reservationList.size() <= 1) {
            return new ArrayList<>();
        }
        Reservation reservation = reservationList.get(0);
        reservationRepository.deleteById(reservation.getReservationId());
        System.out.println("REMOVE!");

        return reservationService.approveReservationList(roomSeq);
    }
}
