package dy.whatsong.domain.music.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dy.whatsong.domain.member.application.service.MemberDetailService;
import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.application.service.MusicRoomService;
import dy.whatsong.domain.music.application.service.check.MusicCheckService;
import dy.whatsong.domain.music.application.service.check.MusicMemberCheckService;
import dy.whatsong.domain.music.application.service.MusicMemberService;
import dy.whatsong.domain.music.dto.request.MusicRequestDTO;
import dy.whatsong.domain.music.dto.response.RoomResponseDTO;
import dy.whatsong.domain.music.entity.*;
import dy.whatsong.domain.music.repo.MusicRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static dy.whatsong.domain.music.entity.QMusicRoom.musicRoom;
import static dy.whatsong.domain.music.entity.QMusicRoomMember.musicRoomMember;

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

	private final MemberDetailService memberDetailService;

	private final MemberCheckService memberCheckService;

	public static final String BAD_REQUEST="Bad Request";

	public static final String ACCESS_ALLOW="Access Allow";

	public static final String ACCESS_DENIED="Access Denied";

	@Override
	@Transactional
	public ResponseEntity<?> createMusicRoom(MusicRequestDTO.Create createDTO) {
		Member findM = memberCheckService.getInfoByMemberSeq(createDTO.getMemberSeq());
		List<MusicRoomMember> mrmList = musicMemberCheckService.getInfoMRMListByMember(findM);
		if(getInfoCreatedRoomLimit(mrmList)) return new ResponseEntity<>("limit", HttpStatus.OK);


		MusicRoom createdRoom = musicRoomRepository.save(
				MusicRoom.builder()
						.roomName(createDTO.getRoomName())
						.category(createDTO.getCategory())
						.accessAuth(createDTO.getAccessAuth())
						.roomCode(UUID.randomUUID().toString())
						.build()
		);

		musicMemberService.createdRoomDetails(createdRoom, findM);

		return new ResponseEntity<>(createdRoom,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getOwnerRoomList(Long memberSeq) {
		QMusicRoomMember qmrm=QMusicRoomMember.musicRoomMember;
		QMusicRoom qmr=QMusicRoom.musicRoom;
		List<MusicRoomMember> fetchResult = jpaQueryFactory
				.selectFrom(qmrm)
				.where(qmrm.ownerSeq.eq(memberSeq))
				.fetch();
		return new ResponseEntity<>(musicCheckService
											.makeListResponseRoomDTO(fetchResult)
													,HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> changeRoomInfo(MusicRequestDTO.ChangeInfo changeInfoDTO) {

		MusicRoom findMR = musicCheckService.getInfoMRBySeq(changeInfoDTO.getRoomSeq());

		if (findMR!=null){
			MusicRoom updateMR = findMR.changeElements(changeInfoDTO);
			return new ResponseEntity<>(updateMR.toChangeInfoDTO(),HttpStatus.OK);
		}

		return new ResponseEntity<>(BAD_REQUEST,HttpStatus.BAD_REQUEST);
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteMusicRoom(MusicRequestDTO.Delete deleteDTO) {
		MusicRoom findMR = musicCheckService.getInfoMRBySeq(deleteDTO.getRoomSeq());
		if (findMR!=null){
			musicMemberService.deletedRoomDetails(findMR);
			musicRoomRepository.delete(findMR);
			return new ResponseEntity<>(findMR.getMusicRoomSeq()+"Deleted",HttpStatus.OK);
		}

		return new ResponseEntity<>(BAD_REQUEST,HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> ableAccessRoom(MusicRequestDTO.AccessRoom accessRoomDTO) {
		QMusicRoom qmr= musicRoom;
		if (isRequestUserRoomOwner(accessRoomDTO)){
			return new ResponseEntity<>(ACCESS_ALLOW,HttpStatus.OK);
		}

		MusicRoom musicRoom = jpaQueryFactory.selectFrom(qmr)
				.where(qmr.musicRoomSeq.eq(accessRoomDTO.getRoomSeq()))
				.fetchOne();

		AccessAuth roomAccess = musicRoom.getAccessAuth();
		switch (roomAccess){
			case NON:
				return new ResponseEntity<>(ACCESS_DENIED,HttpStatus.OK);
			case PUBLIC:
				return new ResponseEntity<>(ACCESS_ALLOW,HttpStatus.OK);
			case PRIVATE:
				return requestPrivateRoomIsCorrect(accessRoomDTO);
		}

		System.out.println("BADREQUEST");
		return new ResponseEntity<>("ERR",HttpStatus.BAD_REQUEST);
	}


	private boolean getInfoCreatedRoomLimit(List<MusicRoomMember> mrmList){
		System.out.println("mrmSize="+mrmList);
		return mrmList.size()==3;
	}


	private boolean isRequestUserRoomOwner(MusicRequestDTO.AccessRoom accessRoomDTO){
		QMusicRoomMember qmrm= musicRoomMember;
		QMusicRoom qmr= musicRoom;
		Member findM = memberCheckService.getInfoByMemberSeq(accessRoomDTO.getMemberSeq());
		BooleanExpression validRoom = qmrm.musicRoom.musicRoomSeq.eq(accessRoomDTO.getRoomSeq())
				.and(qmrm.ownerSeq.eq(findM.getMemberSeq()));

		return jpaQueryFactory.selectOne()
				.from(qmrm)
				.join(qmrm.musicRoom,qmr)
				.where(validRoom)
				.fetchFirst()!=null;
	}
	private ResponseEntity<?> requestPrivateRoomIsCorrect(MusicRequestDTO.AccessRoom accessRoomDTO){
		QMusicRoom qmr=QMusicRoom.musicRoom;
		QMusicRoomMember qmrm=QMusicRoomMember.musicRoomMember;
		MusicRoom eqMR = jpaQueryFactory.selectFrom(qmr)
				.where(qmr.musicRoomSeq.eq(accessRoomDTO.getRoomSeq()))
				.fetchOne();

		Long ownerSeq = jpaQueryFactory.selectFrom(qmrm)
				.where(qmrm.musicRoom.eq(eqMR))
				.fetchOne().getOwnerSeq();
		if(memberDetailService.isOwnerAlreadyFriendsRequest(ownerSeq, accessRoomDTO.getMemberSeq())){
			return new ResponseEntity<>(ACCESS_ALLOW,HttpStatus.OK);
		}

		return new ResponseEntity<>(ACCESS_DENIED,HttpStatus.OK);
	}

	/*public ResponseEntity<?> requestPrivateRoomJPA(Long memberSeq,Long roomSeq){
		Member findM = memberCheckService.getInfoByMemberSeq(memberSeq);
		MusicRoom findMR = musicCheckService.getInfoMRBySeq(roomSeq);
		Long ownerSeq = musicMemberCheckService.getInfoMRMByRoom(findMR).getOwnerSeq();
		if (memberDetailService.isAlreadyFriends(ownerSeq,findM.getMemberSeq())){
			return new ResponseEntity<>(ACCESS_ALLOW,HttpStatus.OK);
		}
		return new ResponseEntity<>(ACCESS_DENIED,HttpStatus.OK);
	}*/
}
