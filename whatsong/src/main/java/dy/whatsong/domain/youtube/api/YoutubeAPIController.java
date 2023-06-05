package dy.whatsong.domain.youtube.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import dy.whatsong.domain.youtube.application.service.YoutubeService;
import dy.whatsong.domain.youtube.dto.VideoDTO;
import dy.whatsong.global.annotation.EssentialController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.server.PathParam;

@EssentialController
@RequiredArgsConstructor
public class YoutubeAPIController {
	private final YoutubeService youtubeService;

	@PostMapping("/youtube/search")
	public ResponseEntity<?> searchByKeywordOnYoutube(@RequestBody VideoDTO.Keyword keywordDTO) throws JsonProcessingException {
		return youtubeService.searchOnYoutube(keywordDTO);
	}
}
