package dy.whatsong.domain.streaming.api;

import dy.whatsong.domain.chat.model.TestDTO;
import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.member.dto.MemberResponseDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.application.service.check.MusicCheckService;
import dy.whatsong.domain.reservation.application.service.ReservationService;
import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.streaming.application.service.StompService;
import dy.whatsong.domain.streaming.dto.MRWSRequest;
import dy.whatsong.domain.streaming.dto.MRWSResponse;
import dy.whatsong.domain.streaming.entity.redis.RoomMember;
import dy.whatsong.domain.streaming.entity.room.Controller;
import dy.whatsong.domain.streaming.entity.room.Status;
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
public class RoomSocketAPI {

    private final String test_username="psb4644@gmail.com";

    private final SimpMessagingTemplate template;

    private final ReservationService reservationService;

    private final MemberCacheService memberCacheService;

    private final MusicCheckService musicCheckService;

    private final StompService stompService;

    private static Status status=Status.PLAYING;


    /*@MessageMapping("/current/info")
    public void currentRoomStateInfoUptoDate(@DestinationVariable String roomCode, @RequestBody TestDTO testDTO){
        System.out.println("소켓 연결!:"+testDTO.getMessage());
//        List<Reservation> reservationList = reservationService.approveReservationList(onlyRoomSeq.getRoomSeq());
        template.convertAndSendToUser(test_username,"/stream/"+roomCode+"/current/info",te);
    }*/

    @MessageMapping("/{roomCode}/music/current/new")
    public void currentRoomStateInfoUptoDate(@DestinationVariable String roomCode, @RequestBody MRWSRequest.updateInfo updateInfoDTO){
        System.out.println("!!!!");
        MRWSResponse.updateInfoRes updateInfoRes;
        if (updateInfoDTO.getStatus()==null){
            updateInfoRes=getUpdateInfoToResDTO(updateInfoDTO);
        }else {
            status=updateInfoDTO.getStatus();
            updateInfoRes=getUpdateInfoToResDTO(updateInfoDTO);
        }
        System.out.println("status:"+status);
        template.convertAndSend("/stream/"+roomCode+"/music/current/info",updateInfoRes);
    }

    @MessageMapping("/{roomCode}/room/enter")
    public void memberEnterTheMusicRoom(@DestinationVariable String roomCode){
        RoomMember roomOfMemberList = memberCacheService.getRoomOfMemberList(roomCode);
        template.convertAndSend("/stream/"+roomCode+"/room/enter",roomOfMemberList);
    }
    
    @MessageMapping("/{roomCode}/room/leave")
    public void memberLeaveTheMusicRoom(@DestinationVariable String roomCode){
        RoomMember roomOfMemberList = memberCacheService.getRoomOfMemberList(roomCode);
        System.out.println("roomCode!!!!!!!="+roomCode);
        template.convertAndSend("/stream/"+roomCode+"/room/leave",roomOfMemberList);
    }

    @MessageMapping("/{roomCode}/room/member")
    public void memberCurrentInRoom(@DestinationVariable String roomCode){
        RoomMember roomOfMemberList = memberCacheService.getRoomOfMemberList(roomCode);
        template.convertAndSend("/stream/"+roomCode+"/room/member",roomOfMemberList);
    }

    @MessageMapping("/{roomCode}/playlist/current/new")
    public void currentRoomStateInfo(@DestinationVariable String roomCode,@RequestBody MRWSRequest.playerCurrentState playerCurrentState){
        List<Reservation> reservationList = reservationService.reservationList(playerCurrentState.getRoomSeq());
        if (playerCurrentState.getController()==null){
            template.convertAndSend("/stream/"+roomCode+"/playlist/current/info",reservationList);
        }else {
            List<Reservation> retuReservationList = stompService.musicSkipInPlaylist(reservationList);
            template.convertAndSend("/stream/"+roomCode+"/playlist/current/info",retuReservationList);
        }
    }

    public MRWSResponse.updateInfoRes getUpdateInfoToResDTO(MRWSRequest.updateInfo updateInfo){
        return MRWSResponse.updateInfoRes.builder()
                .username(updateInfo.getUsername())
                .timeStamp(updateInfo.getTimeStamp())
                .videoId(updateInfo.getVideoId())
                .status(status)
                .build();
    }
}
