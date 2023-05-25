package dy.whatsong.domain.music.dto.request;

import dy.whatsong.domain.music.entity.AccessAuth;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MusicRequestDTO {

	@Getter
	@NoArgsConstructor
	public static class Create{
		private String oauthId;
		private String roomName;
		private String category;
		private AccessAuth accessAuth;
	}
}