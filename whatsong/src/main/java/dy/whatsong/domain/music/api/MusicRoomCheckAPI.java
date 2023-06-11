package dy.whatsong.domain.music.api;

import dy.whatsong.domain.music.application.service.MusicRoomService;
import dy.whatsong.domain.music.application.service.check.MusicCheckService;
import dy.whatsong.global.annotation.EssentialController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/check")
@RequiredArgsConstructor
public class MusicRoomCheckAPI {

	private final MusicRoomService musicRoomService;

	private final MusicCheckService musicCheckService;

	@GetMapping("/all")
	public ResponseEntity<?> getRoomInfoAll(){
		return new ResponseEntity<>(musicCheckService.getInfoListRoom(), HttpStatus.OK);
	}

	@GetMapping("/have")
	public ResponseEntity<?> getRoomInfoHave(@RequestParam("memberSeq") Long memberSeq){
		return musicRoomService.getOwnerRoomList(memberSeq);
	}

	@GetMapping("/limit")
	public ResponseEntity<?> getRoomInfoCreateHave(@RequestParam("memberSeq") Long memberSeq){
		return new ResponseEntity<>(musicCheckService.getInfoRoomLimit(memberSeq),HttpStatus.OK);
	}

	@GetMapping("/room")
	public ResponseEntity<?> getRoomInfo(@RequestParam("roomSeq") Long roomSeq){
		return new ResponseEntity<>(musicCheckService.getInfoMRBySeq(roomSeq),HttpStatus.OK);
	}
}
