package dy.whatsong.domain.youtube.dto;

import lombok.Getter;

@Getter
public class VideoDTO {

	@Getter
	public static class SearchResponse{
		private String videoId;
		private String title;
		private String channelName;
	}
}
