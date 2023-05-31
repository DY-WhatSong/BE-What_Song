package dy.whatsong.domain.youtube.application.impl;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dy.whatsong.domain.youtube.application.service.YoutubeService;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

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
	private static final String API_URL_VIDEOID = "https://www.googleapis.com/youtube/v3/videos";

	@Override
	public void searchOnYoutube(String searchQuery) {
		log.info("KEY="+API_KEY);
		log.info("KEY="+MAX_QUERY);

		String target_url= API_URL_SNIPPET +
				"?part="+YOUTUBE_PART +
				"&maxResult="+MAX_QUERY +
				"&q=" + searchQuery +
				"&key=" + API_KEY +
				"&type="+YOUTUBE_TYPE;

		RestTemplate restTemplate=new RestTemplate();
		String response = restTemplate.getForObject(target_url, String.class);
		System.out.println(response);
		String infoToVideoId = getInfoToSearchDTO(response);
	}

	private void searchByVideoId(String videoId){

	}

	@SneakyThrows
	private String getInfoToSearchDTO(String response) {
		JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
		JsonArray responseInfo = jsonObject.getAsJsonArray("items");
		for (int i=0;i<responseInfo.size();i++){
			System.out.println(responseInfo.get(i).getAsJsonObject().get("id").getAsJsonObject().get("videoId"));
		}
		return null;
	}
}
