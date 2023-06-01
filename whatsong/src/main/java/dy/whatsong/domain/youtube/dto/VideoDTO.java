package dy.whatsong.domain.youtube.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoDTO {

	@Getter
	@Builder
	public static class SearchResponse{
		private String videoId;
		private String title;
		private String channelName;
	}

	@Getter
	public static class Keyword{
		private String keyword;
	}
}
