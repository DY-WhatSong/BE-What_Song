package dy.whatsong.domain.chat.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ChatMessage {

    public ChatMessage() {
    }

    @Builder
    public ChatMessage(MessageType type,
                       long chatRoomSequence,
                       String sender,
                       String message,
                       long userCount) {
        this.type = type;
        this.chatRoomSequence = chatRoomSequence;
        this.sender = sender;
        this.message = message;
        this.userCount = userCount;
    }

    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, QUIT, TALK, EMOJI, LIKE
    }

    private MessageType type; // 메시지 타입
    private long chatRoomSequence; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private long userCount; // 채팅방 인원수, 채팅방 내에서 메시지가 전달될때 인원수 갱신시 사용
}
