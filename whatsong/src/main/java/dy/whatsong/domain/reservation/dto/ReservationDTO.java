package dy.whatsong.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;

public class ReservationDTO {
	@Getter
	public static class Select{
		private String videoId;
		private String title;
		private String channelName;
		private String thumbnailUrl;
		private Long roomSeq;
	}

	@Getter
	@Builder
	public static class SelectVideo{
		private String videoId;
		private String title;
		private String channelName;
		private String thumbnailUrl;
	}

	@Getter
	public static class List{
		private Long roomSeq;
	}
}
