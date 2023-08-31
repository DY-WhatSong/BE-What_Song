package dy.whatsong.domain.streaming.api;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.reservation.application.service.ReservationService;
import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.streaming.dto.MRWSRequest;
import lombok.RequiredArgsConstructor;
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

    private final SimpMessagingTemplate template;

    private final ReservationService reservationService;


    @MessageMapping("/current/info")
    public void currentRoomStateInfoUptoDate(@DestinationVariable String roomCode, @RequestBody MRWSRequest.OnlyRoomSeq onlyRoomSeq){
        System.out.println("소켓 연결!");
        List<Reservation> reservationList = reservationService.approveReservationList(onlyRoomSeq.getRoomSeq());
        template.convertAndSend("/stream/"+roomCode+"/current/info",reservationList);
    }

    @MessageMapping
    public void currentRoomUserInfo(@DestinationVariable String roomCode,@RequestBody MRWSRequest.userEnterState userEnterState){

    }
    @MessageMapping("")
    public void currentRoomStateInfo(){

    }
}
