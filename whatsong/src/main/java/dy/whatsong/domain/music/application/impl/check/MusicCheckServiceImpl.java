package dy.whatsong.domain.music.application.impl.check;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dy.whatsong.domain.music.application.service.check.MusicCheckService;
import dy.whatsong.domain.music.dto.MusicRoomDTO;
import dy.whatsong.domain.music.dto.response.RoomResponseDTO;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.music.entity.MusicRoomMember;
import dy.whatsong.domain.music.entity.QMusicRoom;
import dy.whatsong.domain.music.entity.QMusicRoomMember;
import dy.whatsong.domain.music.repo.MusicRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MusicCheckServiceImpl implements MusicCheckService {

	private final MusicRoomRepository musicRoomRepository;

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public MusicRoom getInfoMRBySeq(Long musicRoomSeq) {
		Optional<MusicRoom> findMR = musicRoomRepository.findById(musicRoomSeq);
		return findMR.orElse(null);
	}

	@Override
	public RoomResponseDTO.BasicRseponse getRoomBasicInfoBySeq(Long musicRoomSeq){
		QMusicRoomMember qmrm=QMusicRoomMember.musicRoomMember;
		MusicRoomMember musicRoomMember = jpaQueryFactory.selectFrom(qmrm)
				.where(qmrm.musicRoom.musicRoomSeq.eq(musicRoomSeq))
				.fetchFirst();
		return makeResponseRoomDTO(musicRoomMember);
	}

	@Override
	public List<RoomResponseDTO.BasicRseponse> getInfoListRoom() {
		QMusicRoomMember qmrm=QMusicRoomMember.musicRoomMember;
		List<MusicRoomMember> fetchResult = jpaQueryFactory.selectFrom(qmrm)
				.fetch();

		return makeListResponseRoomDTO(fetchResult);
	}

	@Override
	public boolean getInfoRoomLimit(Long memberSeq) {
		QMusicRoomMember qmrm=QMusicRoomMember.musicRoomMember;
		return jpaQueryFactory.selectFrom(qmrm)
				.where(qmrm.ownerSeq.eq(memberSeq))
				.fetch()
				.size()==3;
	}

	@Override
	public List<RoomResponseDTO.BasicRseponse> makeListResponseRoomDTO(List<MusicRoomMember> musicRoomMembers){
		return musicRoomMembers
				.stream()
				.map(musicRoomMember -> {
					MusicRoom fetchMusicRoom = musicRoomMember.getMusicRoom();
					return RoomResponseDTO.BasicRseponse.builder()
							.have(fetchMusicRoom.toHaveRoomDTO())
							.extraInfo(RoomResponseDTO.ExtraInfo.builder()
									.hostName(musicRoomMember.getMember().getNickname())
									.view(1)
									.build())
							.build();

				})
				.collect(Collectors.toList());
	}

	private RoomResponseDTO.BasicRseponse makeResponseRoomDTO(MusicRoomMember musicRoomMember){
		return RoomResponseDTO.BasicRseponse
				.builder()
				.have(musicRoomMember.getMusicRoom().toHaveRoomDTO())
				.extraInfo(RoomResponseDTO.ExtraInfo.builder()
						.view(1)
						.hostName(musicRoomMember.getMember().getNickname())
						.hostSeq(musicRoomMember.getMember().getMemberSeq())
						.build())
				.build();
	}
}
