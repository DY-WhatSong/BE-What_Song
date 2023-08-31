package dy.whatsong.domain.streaming.application.impl;

import dy.whatsong.domain.music.application.service.check.MusicCheckService;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.streaming.application.service.RoomWsService;
import dy.whatsong.domain.streaming.entity.room.MRWS;
import dy.whatsong.domain.streaming.entity.room.Status;
import dy.whatsong.domain.streaming.repo.MRSseRepository;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import dy.whatsong.global.handler.exception.InvalidRequestAPIException;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.*;

@EssentialServiceLayer
@RequiredArgsConstructor
public class RoomWsServiceImpl implements RoomWsService {

    private final MRSseRepository mrSseRepository;

    private final MusicCheckService musicCheckService;

    @Override
    public LinkedList<MRWS> getCurrentReservationList(final Reservation reservation) {
        saveCurrentRoomState(reservation);
        return changeIterableToArrayList(mrSseRepository.findAll());
    }

    @Override
    public MRWS getMRSseByRoomCode(String roomCode) {
        System.out.println("RC="+roomCode);
        Optional<MRWS> byId = mrSseRepository.findById(roomCode);
        if (byId.isEmpty()) System.out.println("!!!!");
        return mrSseRepository.findById(roomCode).orElseThrow(()->new InvalidRequestAPIException("Invalid Request",400));
    }

    private LinkedList<MRWS> changeIterableToArrayList(Iterable<MRWS> targetIterable){
        LinkedList<MRWS> currentReservList=new LinkedList<>();
        for (MRWS mrSse : targetIterable) {
            currentReservList.add(mrSse);
        }
        return currentReservList;
    }

    @Transactional
    public MRWS saveCurrentRoomState(final Reservation reservation){
        MusicRoom infoMRBySeq = musicCheckService.getInfoMRBySeq(reservation.getRoomSeq());
        return mrSseRepository.save(
                MRWS.builder()
                        .videoId(reservation.getSelectVideo().getVideoId())
                        .roomCode(infoMRBySeq.getRoomCode())
                        .status(Status.PAUSE)
                        .timestamp("0")
                        .build()
        );
    }
}
