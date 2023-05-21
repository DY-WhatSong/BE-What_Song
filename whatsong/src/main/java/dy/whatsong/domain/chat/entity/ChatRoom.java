package dy.whatsong.domain.chat.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatRoomSeq;

	private String title;

	@OneToMany(mappedBy = "chatRoom")
	private List<ChatRoomMember> chatMemberList;
}
