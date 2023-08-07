package dy.whatsong.domain.streaming.application.service;

import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.streaming.dto.MRSseRequest;
import dy.whatsong.domain.streaming.entity.room.MRSse;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public interface RoomSseService{
    ArrayList<MRSse> getCurrentReservationList(final Reservation reservation);
}
