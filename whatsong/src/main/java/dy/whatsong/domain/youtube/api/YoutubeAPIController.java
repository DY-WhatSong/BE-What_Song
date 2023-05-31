package dy.whatsong.domain.youtube.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import dy.whatsong.domain.youtube.application.service.YoutubeService;
import dy.whatsong.global.annotation.EssentialController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;

@EssentialController
@RequiredArgsConstructor
public class YoutubeAPIController {
	private final YoutubeService youtubeService;

	@PostMapping("/youtube/search")
	public void searchByKeywordOnYoutube(@PathParam("keyword") String keyword) throws JsonProcessingException {
		youtubeService.searchOnYoutube(keyword);
	}
}
