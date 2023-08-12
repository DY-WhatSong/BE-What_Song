package dy.whatsong.domain.chat.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatRoomSeq;

	private String title;

	@OneToMany(mappedBy = "chatRoom")
	private List<ChatRoomMember> chatMemberList;

	//==생성 메서드==//
	public static ChatRoom createChatRoom(String title) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setTitle(title);
		return chatRoom;
	}
}
