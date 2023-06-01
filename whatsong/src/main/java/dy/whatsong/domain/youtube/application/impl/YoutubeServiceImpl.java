package dy.whatsong.domain.youtube.application.impl;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dy.whatsong.domain.youtube.application.service.YoutubeService;
import dy.whatsong.domain.youtube.dto.VideoDTO;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@EssentialServiceLayer
@RequiredArgsConstructor
@Log4j2
public class YoutubeServiceImpl implements YoutubeService {

	@Value("${youtube.apikey}")
	private String API_KEY;

	@Value("${youtube.maxsearch}")
	private String MAX_QUERY;

	private final String YOUTUBE_PART="snippet";
	private final String YOUTUBE_TYPE="video";
	private static final String API_URL_SNIPPET = "https://www.googleapis.com/youtube/v3/search";

	@Override
	public ResponseEntity<?> searchOnYoutube(String searchQuery) {
		log.info("KEY="+API_KEY);
		log.info("KEY="+MAX_QUERY);
		log.info("searchQuery="+searchQuery);
		String target_url= API_URL_SNIPPET +
				"?part="+YOUTUBE_PART +
				"&maxResults="+MAX_QUERY +
				"&q=" + searchQuery +
				"&key=" + API_KEY +
				"&type="+YOUTUBE_TYPE;

		RestTemplate restTemplate=new RestTemplate();
		String response = restTemplate.getForObject(target_url, String.class);
		List<VideoDTO.SearchResponse> infoToSearchDTO = getInfoToSearchDTO(response);
		return new ResponseEntity<>(infoToSearchDTO, HttpStatus.OK);
	}


	@SneakyThrows
	private List<VideoDTO.SearchResponse> getInfoToSearchDTO(String response) {
		List<VideoDTO.SearchResponse> searchList=new ArrayList<>();
		JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
		JsonArray responseInfo = jsonObject.getAsJsonArray("items");
		for (int i=0;i<responseInfo.size();i++){
			JsonObject targetObject = responseInfo.get(i).getAsJsonObject();
			String videoId = targetObject.get("id").getAsJsonObject().get("videoId").getAsString();
			JsonObject snippet = targetObject.get("snippet").getAsJsonObject();
			String title = snippet.get("title").getAsString();
			String channelTitle = snippet.get("channelTitle").getAsString();
			String thumbnailUrl = snippet.get("thumbnails").getAsJsonObject().get("high").getAsJsonObject().get("url").getAsString();
			searchList.add(
					VideoDTO.SearchResponse.builder()
							.videoId(videoId)
							.title(title)
							.channelName(channelTitle)
							.thumbnailUrl(thumbnailUrl)
							.build()
			);
		}
		return searchList;
	}
}
