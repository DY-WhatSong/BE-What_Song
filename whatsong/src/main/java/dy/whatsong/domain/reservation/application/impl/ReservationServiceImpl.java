package dy.whatsong.domain.reservation.application.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dy.whatsong.domain.reservation.application.service.ReservationService;
import dy.whatsong.domain.reservation.dto.ReservationDTO;
import dy.whatsong.domain.reservation.entity.Recognize;
import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.reservation.repo.ReservationRepository;
import dy.whatsong.domain.youtube.dto.VideoDTO;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EssentialServiceLayer
@RequiredArgsConstructor
@Log4j2
public class ReservationServiceImpl implements ReservationService {

	private final JPAQueryFactory jpaQueryFactory;

	private final ReservationRepository reservationRepository;

	@Override
	public ResponseEntity<?> reservationMusic(ReservationDTO.Select selectDTO) {
		Reservation currentReservation = reservationRepository.save(Reservation.builder()
				.reservationId(UUID.randomUUID().toString())
				.roomSeq(selectDTO.getRoomSeq())
				.selectVideo(ReservationDTO.SelectVideo
						.builder()
						.videoId(selectDTO.getVideoId())
						.channelName(selectDTO.getChannelName())
						.thumbnailUrl(selectDTO.getThumbnailUrl())
						.title(selectDTO.getTitle())
						.build())
				.recognize(Recognize.NONE)
				.build());

		return new ResponseEntity<>(currentReservation, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> reservationList(Long roomSeq) {
		List<Reservation> reservationList=new ArrayList<>();
		reservationRepository.findAll()
				.forEach(reservation -> {
					if(reservation.getRoomSeq().equals(roomSeq)) reservationList.add(reservation);
				});
		return new ResponseEntity<>(reservationList,HttpStatus.OK);
	}
}
