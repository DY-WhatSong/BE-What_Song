package dy.whatsong.domain.streaming.application.service;

import dy.whatsong.domain.reservation.entity.Reservation;

import java.util.List;

public interface StompService {
    List<Reservation> musicSkipInPlaylist(List<Reservation> reservationList);
}
