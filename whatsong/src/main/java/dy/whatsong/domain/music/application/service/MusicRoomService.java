package dy.whatsong.domain.music.application.service;

import dy.whatsong.domain.music.dto.request.MusicRequestDTO;
import dy.whatsong.domain.music.entity.MusicRoom;
import org.springframework.http.ResponseEntity;

public interface MusicRoomService {
	ResponseEntity<?> createMusicRoom(MusicRequestDTO.Create createDTO);

	ResponseEntity<?> getOwnerRoomList(MusicRequestDTO.OwnerInfo ownerInfoDTO);

	ResponseEntity<?> changeRoomInfo(MusicRequestDTO.ChangeInfo changeInfoDTO);

	ResponseEntity<?> deleteMusicRoom(MusicRequestDTO.Delete deleteDTO);

	ResponseEntity<?> ableAccessRoom(MusicRequestDTO.AccessRoom accessRoomDTO);
}