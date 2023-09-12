package dy.whatsong.domain.streaming.api;

import dy.whatsong.domain.chat.model.TestDTO;
import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.member.dto.MemberResponseDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.application.service.check.MusicCheckService;
import dy.whatsong.domain.reservation.application.service.ReservationService;
import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.streaming.dto.MRWSRequest;
import dy.whatsong.domain.streaming.entity.room.Controller;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Test;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/{roomCode}")
public class RoomSocketAPI {

    private final String test_username="psb4644@gmail.com";

    private final SimpMessagingTemplate template;

    private final ReservationService reservationService;

    private final MemberCacheService memberCacheService;

    private final MusicCheckService musicCheckService;


    /*@MessageMapping("/current/info")
    public void currentRoomStateInfoUptoDate(@DestinationVariable String roomCode, @RequestBody MRWSRequest.OnlyRoomSeq onlyRoomSeq){
        System.out.println("소켓 연결!");
        List<Reservation> reservationList = reservationService.approveReservationList(onlyRoomSeq.getRoomSeq());
        template.convertAndSend("/stream/"+roomCode+"/current/info",reservationList);
    }*/

    @MessageMapping("/member/update/new")
    public void currentRoomStateInfoUptoDate(@DestinationVariable String roomCode, @RequestBody TestDTO testDTO){
        System.out.println("message:"+testDTO.getMessage());
        template.convertAndSend("/stream/"+roomCode+"/current/info",testDTO);
    }

    @MessageMapping("/room/enter")
    public void memberEnterTheMusicRoom(@DestinationVariable String roomCode,@RequestBody MRWSRequest.OnlyMemberSeq onlyMemberSeq){
        /*if (memberCacheService.memberIfExistEnter(onlyMemberSeq.getMemberSeq(),roomCode)){
            System.out.println("이미 방에 있는 유저");
        }*/
        List<MemberResponseDto.CheckResponse> nowMemberInRoom = memberCacheService.putMemberInCacheIfEmpty(roomCode, onlyMemberSeq.getMemberSeq());
        template.convertAndSend("/stream/"+roomCode+"/room/enter",nowMemberInRoom);
    }
    
    @MessageMapping("/room/leave")
    public void memberLeaveTheMusicRoom(@DestinationVariable String roomCode,@RequestBody MRWSRequest.OnlyMemberSeq onlyMemberSeq){
        List<MemberResponseDto.CheckResponse> nowMemberInRoom = memberCacheService.leaveMemberInCache(roomCode, onlyMemberSeq.getMemberSeq());
        template.convertAndSend("/stream/"+roomCode+"/room/leve",nowMemberInRoom);
    }

    @MessageMapping("/room/info/current")
    public void currentRoomStateInfo(@DestinationVariable String roomCode,@RequestBody MRWSRequest.playerCurrentState playerCurrentState){
        if (playerCurrentState.getController().equals(Controller.CURRENT)){
            List<Reservation> reservationList = reservationService.approveReservationList(playerCurrentState.getRoomSeq());
            template.convertAndSend("/stream/"+roomCode+"/info/current",reservationList);
        }
        else if (playerCurrentState.getController().equals(Controller.NEXT)){

        }
    }
}
