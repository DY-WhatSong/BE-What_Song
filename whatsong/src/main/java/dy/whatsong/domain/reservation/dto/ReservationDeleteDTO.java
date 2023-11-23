package dy.whatsong.domain.reservation.dto;

import dy.whatsong.domain.reservation.entity.Recognize;
import lombok.Getter;

@Getter
public class ReservationDeleteDTO {
    private String reservationId;
    private Recognize recognize;
}
