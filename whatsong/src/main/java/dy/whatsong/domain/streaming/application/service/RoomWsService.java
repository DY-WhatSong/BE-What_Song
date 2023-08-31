package dy.whatsong.domain.streaming.application.service;

import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.streaming.entity.room.MRWS;

import java.util.LinkedList;

public interface RoomWsService {
    LinkedList<MRWS> getCurrentReservationList(final Reservation reservation);

    MRWS getMRSseByRoomCode(String roomCode);
}
