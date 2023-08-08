package dy.whatsong.domain.streaming.application.impl;

import dy.whatsong.domain.music.api.MusicRoomCheckAPI;
import dy.whatsong.domain.music.application.service.check.MusicCheckService;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.streaming.application.service.RoomSseService;
import dy.whatsong.domain.streaming.dto.MRSseRequest;
import dy.whatsong.domain.streaming.entity.room.MRSse;
import dy.whatsong.domain.streaming.entity.room.Status;
import dy.whatsong.domain.streaming.repo.MRSseRepository;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import dy.whatsong.global.handler.exception.InvalidRequestAPIException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.*;

@EssentialServiceLayer
@RequiredArgsConstructor
public class RoomSseServiceImpl implements RoomSseService {

    private final MRSseRepository mrSseRepository;

    private final MusicCheckService musicCheckService;

    @Override
    public LinkedList<MRSse> getCurrentReservationList(final Reservation reservation) {
        saveCurrentRoomState(reservation);
        return changeIterableToArrayList(mrSseRepository.findAll());
    }

    @Override
    public MRSse getMRSseByRoomCode(String roomCode) {
        System.out.println("RC="+roomCode);
        Optional<MRSse> byId = mrSseRepository.findById(roomCode);
        if (byId.isEmpty()) System.out.println("!!!!");
        return mrSseRepository.findById(roomCode).orElseThrow(()->new InvalidRequestAPIException("Invalid Request",400));
    }

    private LinkedList<MRSse> changeIterableToArrayList(Iterable<MRSse> targetIterable){
        LinkedList<MRSse> currentReservList=new LinkedList<>();
        for (MRSse mrSse : targetIterable) {
            currentReservList.add(mrSse);
        }
        return currentReservList;
    }

    @Transactional
    public MRSse saveCurrentRoomState(final Reservation reservation){
        MusicRoom infoMRBySeq = musicCheckService.getInfoMRBySeq(reservation.getRoomSeq());
        return mrSseRepository.save(
                MRSse.builder()
                        .videoId(reservation.getSelectVideo().getVideoId())
                        .roomCode(infoMRBySeq.getRoomCode())
                        .status(Status.PAUSE)
                        .timestamp("0")
                        .build()
        );
    }
}