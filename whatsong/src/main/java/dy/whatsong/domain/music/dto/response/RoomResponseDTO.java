package dy.whatsong.domain.music.dto.response;

import dy.whatsong.domain.music.entity.AccessAuth;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class RoomResponseDTO {

	@Getter
	@Builder
	public static class Have{
		private Long musicRoomSeq;

		private String roomName;

		private String roomCode;

		private String category;

		@Enumerated(EnumType.STRING)
		private AccessAuth accessAuth;
	}

	@Getter
	@Builder
	public static class ExtraInfo{
		private Long hostSeq;
		private String hostName;
		private long view;
	}

	@Getter
	@Builder
	public static class Change{
		private Long musicRoomSeq;

		private String roomName;

		private String roomCode;

		private String category;

		@Enumerated(EnumType.STRING)
		private AccessAuth accessAuth;
	}

	@Getter
	@Builder
	public static class BasicRseponse{
		private Have have;
		private ExtraInfo extraInfo;
	}
}
