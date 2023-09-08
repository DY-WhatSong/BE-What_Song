package dy.whatsong.domain.chat.api;

import dy.whatsong.domain.chat.model.ChatMessage;
import dy.whatsong.domain.chat.repo.ChatRoomRepository;
import dy.whatsong.domain.chat.service.ChatService;
import dy.whatsong.domain.member.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final TokenService tokenService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    /**
     * websocket "/app/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("authorization") String token) {
        log.info("message : {}, token : {}", message, token);
        String uniqueUserName = tokenService.getOauthIdAndSocialType(token);
        if(uniqueUserName != null) {
            // 로그인 회원 정보로 대화명 설정
            message.setSender(uniqueUserName);
            // 채팅방 인원수 세팅
            message.setUserCount(chatRoomRepository.getUserCount(message.getChatRoomSequence()));
            // Websocket에 발행된 메시지를 redis로 발행(publish)
            chatService.sendChatMessage(message);
        }
    }
}
