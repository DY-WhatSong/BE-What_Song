package dy.whatsong.domain.story.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class StoryVideo implements Serializable {
    private String videoId;
    private String title;
    private String channelName;
    private String thumbnailUrl;

    @Override
    public String toString() {
        return "{" +
                "\"videoId\":\"" + videoId + "\"," +
                "\"title\":\"" + title + "\"," +
                "\"channelName\":\"" + channelName + "\"," +
                "\"thumbnailUrl\":\"" + thumbnailUrl + "\"" +
                "}";
    }
}
