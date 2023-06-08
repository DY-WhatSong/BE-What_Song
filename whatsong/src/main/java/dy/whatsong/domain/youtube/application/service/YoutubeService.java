package dy.whatsong.domain.youtube.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dy.whatsong.domain.youtube.dto.VideoDTO;
import org.springframework.http.ResponseEntity;

public interface YoutubeService {
	ResponseEntity<?> searchOnYoutube(VideoDTO.Keyword keywordDTO) throws JsonProcessingException;
}
