package dy.whatsong.domain.music.application.impl;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.application.service.MusicRoomService;
import dy.whatsong.domain.music.application.service.check.MusicMemberCheckService;
import dy.whatsong.domain.music.application.service.check.MusicMemberService;
import dy.whatsong.domain.music.dto.request.MusicRequestDTO;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.music.entity.MusicRoomMember;
import dy.whatsong.domain.music.repo.MusicRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class MusicRoomServiceImpl implements MusicRoomService {

	private final MusicRoomRepository musicRoomRepository;

	private final MusicMemberService musicMemberService;

	private final MusicMemberCheckService musicMemberCheckService;

	//TODO : 회원데이터 추후 진짜 회원 도메인에서 받아오기
	Member member=new Member();


	@Override
	@Transactional
	public ResponseEntity<?> createMusicRoom(MusicRequestDTO.Create createDTO) {
		List<MusicRoomMember> mrmList = musicMemberCheckService.getInfoMRMListByMember(member);
		if(getInfoCreatedRoomLimit(mrmList)) return new ResponseEntity<>("limit", HttpStatus.OK);


		MusicRoom createdRoom = musicRoomRepository.save(
				MusicRoom.builder()
						.roomName(createDTO.getRoomName())
						.category(createDTO.getCategory())
						.accessAuth(createDTO.getAccessAuth())
						.roomCode(UUID.randomUUID().toString())
						.build()
		);

		musicMemberService.createdRoomDetails(createdRoom,member);

		return new ResponseEntity<>(createdRoom,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getOwnerRoomList(MusicRequestDTO.OwnerInfo ownerInfoDTO) {
		List<MusicRoomMember> mrmList = musicMemberCheckService.getInfoMRMListByMember(member);

	}

	@Override
	public ResponseEntity<?> changeRoomInfo(MusicRequestDTO.ChangeInfo changeInfoDTO) {
		return null;
	}

	@Override
	public ResponseEntity<?> deleteMusicRoom(MusicRequestDTO.Delete deleteDTO) {
		return null;
	}

	private


	private boolean getInfoCreatedRoomLimit(List<MusicRoomMember> mrmList){
		return mrmList.size()==3;
	}

}
