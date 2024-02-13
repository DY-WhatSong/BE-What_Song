package dy.whatsong.domain.chat.api.handler;

import dy.whatsong.domain.chat.repo.ChatRoomRepository;
import dy.whatsong.domain.chat.service.ChatService;
import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.member.dto.MemberRequestCacheDTO;
import dy.whatsong.domain.member.service.oauth.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final MemberCacheService memberCacheService;
    private final OauthService oauthService;

    // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand commandType = accessor.getCommand();
        String destinationUrl = accessor.getDestination();
        String jwtToken = accessor.getFirstNativeHeader("Authorization");
        // STOMP 프레임의 목적지(Destination) 추출
        if (StompCommand.CONNECT == commandType) { // websocket 연결요청
            System.out.println("Connect");
            log.info("CONNECT {}", jwtToken);
            // Header의 jwt token 검증
        } else if (StompCommand.SUBSCRIBE == commandType) { // 채팅룸 구독요청
            // header정보에서 구독 destination정보를 얻고, roomId를 추출한다.
            String chatRoomSequence = chatService.getChatRoomSequence(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidChatRoomSequence"));
            // 채팅방에 들어온 클라이언트 sessionId를 chatRoomSequence와 맵핑해 놓는다.(나중에 특정 세션이 어떤 채팅방에 들어가 있는지 알기 위함)
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            chatRoomRepository.setUserEnterInfo(sessionId, chatRoomSequence);
            // 채팅방의 인원수를 +1한다.
            chatRoomRepository.plusUserCount(chatRoomSequence);
            // 클라이언트 입장 메시지를 채팅방에 발송한다.(redis publish)
            String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");
            /*chatService.sendChatMessage(ChatMessage.builder()
                                            .type(ChatMessage.MessageType.ENTER)
                                            .chatRoomSequence(chatRoomSequence)
                                            .sender(name)
                                            .build());*/
            log.info("SUBSCRIBED {}, {}", name, chatRoomSequence);
        } else if (StompCommand.DISCONNECT == commandType) { // Websocket 연결 종료
            // 연결이 종료된 클라이언트 sesssionId로 채팅방 id를 얻는다.
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            String chatRoomSequence = chatRoomRepository.getUserEnterChatRoomSequence(sessionId);
            // 채팅방의 인원수를 -1한다.
            chatRoomRepository.minusUserCount(chatRoomSequence);
            // 클라이언트 퇴장 메시지를 채팅방에 발송한다.(redis publish)
            String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");
            /*chatService.sendChatMessage(ChatMessage.builder()
                                            .type(ChatMessage.MessageType.QUIT)
                                            .chatRoomSequence(chatRoomSequence)
                                            .sender(name)
                                            .build());*/
            // 퇴장한 클라이언트의 chatRoomSequence 맵핑 정보를 삭제한다.
            chatRoomRepository.removeUserEnterInfo(sessionId);
            log.info("DISCONNECTED {}, {}", sessionId, chatRoomSequence);
        } else if (StompCommand.SEND == commandType) {
            System.out.println("url:" + destinationUrl);
            if (destinationUrl.contains("enter")) {
                memberCacheService.putMemberInCacheIfEmpty(processMemberCache(destinationUrl, jwtToken));
            } else if (destinationUrl.contains("leave")) {
                memberCacheService.leaveMemberInCache(processMemberCache(destinationUrl, jwtToken));
            }
        }
        return message;
    }

    public String getUsernameByTokenDecode(String accessToken) {
        return oauthService.getMe(accessToken).email();
    }

    public String eliminateInBearerToken(String accessToken) {
        return accessToken.replaceAll("Bearer ", "");
    }

    public String getRoomCodeInDestUrl(String destinationUrl) {
        Matcher matcher = Pattern.compile("/app/([a-fA-F0-9-]+)/").matcher(destinationUrl);
        return matcher.find() ? matcher.group(1) : null;
    }

    public MemberRequestCacheDTO.BasicInfo processMemberCache(String destinationUrl, String jwtToken) {
        return MemberRequestCacheDTO.BasicInfo.builder()
                .roomCode(getRoomCodeInDestUrl(destinationUrl))
                .username(getUsernameByTokenDecode(jwtToken))
                .build();
    }
}
