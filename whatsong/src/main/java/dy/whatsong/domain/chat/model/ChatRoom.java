package dy.whatsong.domain.chat.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private long chatRoomSequence;
    private String title;
    private long userCount; // 채팅방 인원수

    public static ChatRoom create(String title) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.title = title;
        return chatRoom;
    }
}
