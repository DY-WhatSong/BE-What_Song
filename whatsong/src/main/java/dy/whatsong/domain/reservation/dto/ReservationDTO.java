package dy.whatsong.domain.reservation.dto;

import dy.whatsong.domain.reservation.entity.Recognize;
import lombok.AllArgsConstructor;
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
		private Long memberSeq;
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

	@Getter
	public static class Approve{
		private String reservationId;
		private Recognize recognize;

		public Approve(String reservationId, Recognize recognize) {
			this.reservationId = reservationId;
			this.recognize = recognize;
		}
	}
}
