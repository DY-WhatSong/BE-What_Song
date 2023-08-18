package dy.whatsong.domain.streaming.api;

import dy.whatsong.domain.member.entity.Member;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channels/stream/room/{roomCode}")
public class RoomSocketAPI {

    private final SimpMessagingTemplate template;

    private static Map<String, Member> currentUserInfo=new LinkedHashMap<>();

    @MessageMapping("/current/info")
    public void currentRoomStateInfo(@DestinationVariable String roomCode, @RequestBody MRWSRequest mrwsRequest){
        System.out.println("소켓 연결!");
        template.convertAndSend("/sub/"+roomCode+"/current/info",mrwsRequest);
    }

    /*@MessageMapping
    public void currentRoomUserInfo(@DestinationVariable String roomCode,)*/
}
