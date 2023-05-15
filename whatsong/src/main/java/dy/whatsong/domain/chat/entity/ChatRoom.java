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
	private Long roomSeq;

	private String title;

	private int limit;

	private String roomCode;

	@OneToMany(mappedBy = "chatRoom")
	private List<RoomMember> chatMemberList;
}
