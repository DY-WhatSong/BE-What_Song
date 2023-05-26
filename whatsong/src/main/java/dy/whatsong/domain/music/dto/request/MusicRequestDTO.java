package dy.whatsong.domain.music.dto.request;

import dy.whatsong.domain.music.entity.AccessAuth;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MusicRequestDTO {

	@Getter
	public static class Create{
		private String oauthId;
		private String roomName;
		private String category;
		private AccessAuth accessAuth;
	}

	@Getter
	public static class OwnerInfo{
		private String oauthId;
	}

	@Getter
	public static class ChangeInfo{
		private Long roomSeq;
		private String name;
		private String category;
		private AccessAuth accessAuth;
	}

	@Getter
	public static class Delete{
		private Long roomSeq;
	}
}