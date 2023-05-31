package dy.whatsong.domain.youtube.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface YoutubeService {
	void searchOnYoutube(String searchQuery) throws JsonProcessingException;
}
