package dy.whatsong.domain.music.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long musicRoomSeq;

	private String roomName;

	private String roomCode;

	private String category;

	private Integer count;

	@Enumerated(EnumType.STRING)
	private AccessAuth accessAuth;

	@OneToMany(mappedBy = "musicRoom")
	private List<MusicRoomMember> musicRoomMembers;
}
