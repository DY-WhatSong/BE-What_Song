package dy.whatsong.domain.streaming.api;

import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.reservation.application.service.ReservationService;
import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.streaming.application.service.StompService;
import dy.whatsong.domain.streaming.dto.MRWSRequest;
import dy.whatsong.domain.streaming.dto.MRWSResponse;
import dy.whatsong.domain.streaming.entity.redis.RoomMember;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomSocketAPI {

    private final SimpMessagingTemplate template;

    private final ReservationService reservationService;

    private final MemberCacheService memberCacheService;

    private final StompService stompService;

    /*@MessageMapping("/current/info")
    public void currentRoomStateInfoUptoDate(@DestinationVariable String roomCode, @RequestBody TestDTO testDTO){
        System.out.println("소켓 연결!:"+testDTO.getMessage());
//        List<Reservation> reservationList = reservationService.approveReservationList(onlyRoomSeq.getRoomSeq());
        template.convertAndSendToUser(test_username,"/stream/"+roomCode+"/current/info",te);
    }*/

    @MessageMapping("/{roomCode}/music/current/new")
    public void currentRoomStateInfoUptoDate(@DestinationVariable String roomCode, @RequestBody MRWSRequest.updateInfo updateInfoDTO) {
        System.out.println("!!!!");
        System.out.println("status:" + updateInfoDTO.getStatus());
        MRWSResponse.updateInfoRes updateInfoRes = getUpdateInfoToResDTO(updateInfoDTO);
        template.convertAndSend("/stream/" + roomCode + "/music/current/info", updateInfoRes);
    }

    @MessageMapping("/{roomCode}/room/enter")
    public void memberEnterTheMusicRoom(@DestinationVariable String roomCode) {
        RoomMember roomOfMemberList = memberCacheService.getRoomOfMemberList(roomCode);
        template.convertAndSend("/stream/" + roomCode + "/room/enter", roomOfMemberList);
    }

    @MessageMapping("/{roomCode}/room/leave")
    public void memberLeaveTheMusicRoom(@DestinationVariable String roomCode) {
        RoomMember roomOfMemberList = memberCacheService.getRoomOfMemberList(roomCode);
        System.out.println("roomCode!!!!!!!=" + roomCode);
        template.convertAndSend("/stream/" + roomCode + "/room/leave", roomOfMemberList);
    }

    @MessageMapping("/{roomCode}/room/member")
    public void memberCurrentInRoom(@DestinationVariable String roomCode) {
        RoomMember roomOfMemberList = memberCacheService.getRoomOfMemberList(roomCode);
        template.convertAndSend("/stream/" + roomCode + "/room/member", roomOfMemberList);
    }

    @MessageMapping("/{roomCode}/playlist/current/new")
    public void currentRoomStateInfo(@DestinationVariable String roomCode, @RequestBody MRWSRequest.playerCurrentState playerCurrentState) {
        List<Reservation> reservationList = reservationService.approveReservationList(playerCurrentState.getRoomSeq());
        System.out.println("제일중요=" + reservationList);
        if (playerCurrentState.getController() == null) {
            template.convertAndSend("/stream/" + roomCode + "/playlist/current/info", reservationList);
        } else {
            List<Reservation> retuReservationList = stompService.musicSkipInPlaylist(reservationList);
            template.convertAndSend("/stream/" + roomCode + "/playlist/current/info", retuReservationList);
        }
    }

    public MRWSResponse.updateInfoRes getUpdateInfoToResDTO(MRWSRequest.updateInfo updateInfo) {
        return MRWSResponse.updateInfoRes.builder()
                .username(updateInfo.getUsername())
                .timeStamp(updateInfo.getTimeStamp())
                .videoId(updateInfo.getVideoId())
                .status(updateInfo.getStatus())
                .build();
    }
}
