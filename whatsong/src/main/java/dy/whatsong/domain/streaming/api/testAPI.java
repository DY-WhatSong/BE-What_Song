package dy.whatsong.domain.streaming.api;

import dy.whatsong.domain.streaming.dto.MRWSRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class testAPI {
    private final SimpMessagingTemplate template;

    @MessageMapping("/test")
    public void currentRoomStateInfo(){
        System.out.println("소켓 연결!");
        template.convertAndSend("/stream/test","Success!");
    }

    @MessageMapping("/post/test")
    public void testPost(@RequestBody String testWord){
        System.out.println("TesT!");
        template.convertAndSend("/stream/test/post",testWord);
    }
}
