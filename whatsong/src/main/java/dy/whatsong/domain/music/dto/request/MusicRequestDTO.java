package dy.whatsong.domain.music.dto.request;

import dy.whatsong.domain.music.entity.AccessAuth;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MusicRequestDTO {

	@Getter
	@Builder
	public static class Create{
		private Long memberSeq;
		private String roomName;
		private String category;
		private AccessAuth accessAuth;
	}

	/*@Getter
	public static class OwnerInfo{
		private Long memberSeq;
	}*/

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

	@Getter
	@Builder
	public static class AccessRoom{
		private Long memberSeq;
		private Long roomSeq;
	}
}