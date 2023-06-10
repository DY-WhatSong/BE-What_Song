package dy.whatsong.domain.reservation.entity;

import dy.whatsong.domain.reservation.dto.ReservationDTO;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.springframework.data.annotation.Id;


@RedisHash(value = "reservation",timeToLive = 86400)
@Getter
@Builder
public class Reservation {

	@Id
	private String reservationId;

	private Long roomSeq;

	private ReservationDTO.SelectVideo selectVideo;

	@Enumerated(EnumType.STRING)
	private Recognize recognize;
}
