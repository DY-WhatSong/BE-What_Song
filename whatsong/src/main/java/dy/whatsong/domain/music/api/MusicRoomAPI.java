package dy.whatsong.domain.music.api;

import dy.whatsong.domain.music.application.service.MusicRoomService;
import dy.whatsong.domain.music.dto.request.MusicRequestDTO;
import dy.whatsong.global.annotation.EssentialController;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@EssentialController
@RequiredArgsConstructor
public class MusicRoomAPI {
	private final MusicRoomService musicRoomService;

	@PostMapping("/musicRoom")
	public ResponseEntity<?> createMusicRoom(@RequestBody MusicRequestDTO.Create createDTO){
		return musicRoomService.createMusicRoom(createDTO);
	}

	@PatchMapping("/musicRoom")
	public ResponseEntity<?> changeRoomInfo(@RequestBody MusicRequestDTO.ChangeInfo changeInfoDTO){
		return musicRoomService.changeRoomInfo(changeInfoDTO);
	}

	@DeleteMapping("/musicRoom")
	public ResponseEntity<?> deletedMusicRoom(@RequestBody MusicRequestDTO.Delete deleteDTO){
		return musicRoomService.deleteMusicRoom(deleteDTO);
	}

	@PostMapping("/musicRoom/access")
	public ResponseEntity<?> accessMusicRoom(@RequestBody MusicRequestDTO.AccessRoom accessRoomDTO){
		return musicRoomService.ableAccessRoom(accessRoomDTO);
	}
}
