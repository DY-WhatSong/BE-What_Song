package dy.whatsong.domain.streaming.application.impl;

import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.streaming.application.service.StompService;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@EssentialServiceLayer
public class StompServiceImpl implements StompService {
    @Override
    public List<Reservation> musicSkipInPlaylist(List<Reservation> reservationList) {
        if (reservationList.size()<=1){
            return new ArrayList<>();
        }

        reservationList.remove(0);
        return reservationList;
    }
}
