package dy.whatsong.domain.music.dto;

import dy.whatsong.domain.music.entity.AccessAuth;
import dy.whatsong.domain.music.entity.MusicRoomMember;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
public class MusicRoomDTO {
	private Long musicRoomSeq;

	private String roomName;

	private String roomCode;

	private String category;

	@Enumerated(EnumType.STRING)
	private AccessAuth accessAuth;

	private List<MusicRoomMember> musicRoomMembers;
}
