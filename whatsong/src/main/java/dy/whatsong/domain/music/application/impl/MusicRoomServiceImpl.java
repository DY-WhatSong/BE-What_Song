package dy.whatsong.domain.music.application.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.application.service.MusicRoomService;
import dy.whatsong.domain.music.application.service.check.MusicCheckService;
import dy.whatsong.domain.music.application.service.check.MusicMemberCheckService;
import dy.whatsong.domain.music.application.service.MusicMemberService;
import dy.whatsong.domain.music.dto.request.MusicRequestDTO;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.music.entity.MusicRoomMember;
import dy.whatsong.domain.music.entity.QMusicRoom;
import dy.whatsong.domain.music.entity.QMusicRoomMember;
import dy.whatsong.domain.music.repo.MusicRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class MusicRoomServiceImpl implements MusicRoomService {

	private final MusicRoomRepository musicRoomRepository;

	private final MusicMemberService musicMemberService;

	private final MusicMemberCheckService musicMemberCheckService;

	private final MusicCheckService musicCheckService;

	private final JPAQueryFactory jpaQueryFactory;

	public static final String BAD_REQUEST="Bad Request";

	//TODO : 회원데이터 추후 진짜 회원 도메인에서 받아오기
	Member dummyMember =new Member();


	@Override
	@Transactional
	public ResponseEntity<?> createMusicRoom(MusicRequestDTO.Create createDTO) {
		List<MusicRoomMember> mrmList = musicMemberCheckService.getInfoMRMListByMember(dummyMember);
		if(getInfoCreatedRoomLimit(mrmList)) return new ResponseEntity<>("limit", HttpStatus.OK);


		MusicRoom createdRoom = musicRoomRepository.save(
				MusicRoom.builder()
						.roomName(createDTO.getRoomName())
						.category(createDTO.getCategory())
						.accessAuth(createDTO.getAccessAuth())
						.roomCode(UUID.randomUUID().toString())
						.build()
		);

		musicMemberService.createdRoomDetails(createdRoom, dummyMember);

		return new ResponseEntity<>(createdRoom,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getOwnerRoomList(MusicRequestDTO.OwnerInfo ownerInfoDTO) {
		QMusicRoomMember qmr=QMusicRoomMember.musicRoomMember;
		QMusicRoom qm=QMusicRoom.musicRoom;
		List<MusicRoom> musicRoomList = jpaQueryFactory.select(qm)
				.from(qmr)
				.join(qmr.musicRoom, qm)
				.where(qmr.member.eq(dummyMember))
				.fetch();
		return new ResponseEntity<>(musicRoomList,HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> changeRoomInfo(MusicRequestDTO.ChangeInfo changeInfoDTO) {

		MusicRoom findMR = musicCheckService.getInfoMRBySeq(changeInfoDTO.getRoomSeq());

		if (findMR!=null){
			MusicRoom updateMR = findMR.changeElements(changeInfoDTO);
			return new ResponseEntity<>(updateMR,HttpStatus.OK);
		}

		return new ResponseEntity<>(BAD_REQUEST,HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> deleteMusicRoom(MusicRequestDTO.Delete deleteDTO) {
		MusicRoom findMR = musicCheckService.getInfoMRBySeq(deleteDTO.getRoomSeq());
		if (findMR!=null){
			musicRoomRepository.delete(findMR);
			return new ResponseEntity<>(findMR.getMusicRoomSeq()+"Deleted",HttpStatus.OK);
		}

		return new ResponseEntity<>(BAD_REQUEST,HttpStatus.BAD_REQUEST);
	}


	private boolean getInfoCreatedRoomLimit(List<MusicRoomMember> mrmList){
		return mrmList.size()==3;
	}

}
