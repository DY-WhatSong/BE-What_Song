package dy.whatsong.domain.reservation.application.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dy.whatsong.domain.music.application.service.MusicMemberService;
import dy.whatsong.domain.reservation.application.service.ReservationService;
import dy.whatsong.domain.reservation.dto.ReservationDTO;
import dy.whatsong.domain.reservation.entity.Recognize;
import dy.whatsong.domain.reservation.entity.Reservation;
import dy.whatsong.domain.reservation.repo.ReservationRepository;
import dy.whatsong.domain.streaming.application.service.RoomWsService;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import dy.whatsong.global.handler.exception.InvalidRequestAPIException;
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

	private final MusicMemberService musicMemberService;

	private final RoomWsService roomWsService;

	@Override
	public ResponseEntity<?> reservationMusic(ReservationDTO.Select selectDTO) {
		System.out.println("대기열 등록");
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
		if (musicMemberService.memberIsRoomOwner(selectDTO.getMemberSeq(), selectDTO.getRoomSeq())){
			approveReservation(new ReservationDTO.Approve(currentReservation.getReservationId(),Recognize.APPROVE));
		}
		return new ResponseEntity<>(currentReservation, HttpStatus.OK);
	}

	@Override
	public List<Reservation> reservationList(Long roomSeq) {
		System.out.println("roomSeq="+roomSeq);
		List<Reservation> reservationList=new ArrayList<>();

		reservationRepository.findAll()
				.forEach(reservation -> {
					if(Optional.ofNullable(reservation).isPresent()&&reservation.getRoomSeq().equals(roomSeq)&&reservation.getRecognize().equals(Recognize.NONE)){
						reservationList.add(reservation);
					}
				});
		return reservationList;
	}

	@Override
	public List<Reservation> approveReservationList(Long roomSeq) {
		List<Reservation> reservationList=new ArrayList<>();
		reservationRepository.findAll()
				.forEach(reservation -> {
					if (Optional.ofNullable(reservation).isPresent()&&reservation.getRoomSeq().equals(roomSeq)&&reservation.getRecognize().equals(Recognize.APPROVE)){
						reservationList.add(reservation);
					}
				});

		return reservationList;
	}



	/**
		승인 뿐 아니라 거절 되면 리스트 삭제 또한 구현되어야함

	 	이로 인해 승인리스트와 대기열 리스트를 별도의 도메인으로 분류해야한다고 판단
	*/
	@Override
	public ResponseEntity<?> approveReservation(final ReservationDTO.Approve approveDTO) {
		Optional<Reservation> findOptionReservation = reservationRepository.findById(approveDTO.getReservationId());
		if (findOptionReservation.isEmpty()){
			throw new InvalidRequestAPIException("Invalid Request",400);
		}
		Reservation reSaveReserv = reSaveReservationEntity(
				findOptionReservation.get()
				, approveDTO.getRecognize());
		return new ResponseEntity<>(
					roomWsService.getCurrentReservationList(reSaveReserv)
					,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> musicSkipByController(Long roomSeq) {
		return null;
	}

	private Reservation reSaveReservationEntity(final Reservation reservation,final Recognize changeRecognize){
		Reservation changeReserEntity = Reservation.builder()
				.reservationId(reservation.getReservationId())
				.selectVideo(reservation.getSelectVideo())
				.recognize(changeRecognize)
				.roomSeq(reservation.getRoomSeq())
				.build();

		return reservationRepository.save(changeReserEntity);
	}
}
