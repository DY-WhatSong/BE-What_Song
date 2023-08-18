package dy.whatsong.domain.music.dto;

import dy.whatsong.domain.music.entity.AccessAuth;
import dy.whatsong.domain.music.entity.MusicRoomMember;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Builder
public class MusicRoomDTO {
	private Long musicRoomSeq;

	private String roomName;

	private String roomCode;

	private String category;

	@Enumerated(EnumType.STRING)
	private AccessAuth accessAuth;

}
