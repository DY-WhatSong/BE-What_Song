package dy.whatsong.domain.youtube.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface YoutubeService {
	ResponseEntity<?> searchOnYoutube(String searchQuery) throws JsonProcessingException;
}
