package dy.whatsong.domain.music.api;

import com.google.zxing.WriterException;
import dy.whatsong.domain.music.application.service.MusicRoomService;
import dy.whatsong.global.annotation.EssentialController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@EssentialController
@RequiredArgsConstructor
public class MusicRoomDetailAPI {

	private final MusicRoomService musicRoomService;

	@GetMapping("/musicroom/share")
	public ResponseEntity<?> getQRCode(@RequestParam("roomSeq") Long roomSeq) throws IOException, WriterException {
		return musicRoomService.shareToQRorLink(roomSeq);
	}
}
