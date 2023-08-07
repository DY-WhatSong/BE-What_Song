package dy.whatsong.domain.streaming.application.service;

import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.streaming.entity.room.MRSse;

import java.util.LinkedList;

public interface RoomSseService{
    LinkedList<MRSse> getCurrentReservationList(final Reservation reservation);
}
