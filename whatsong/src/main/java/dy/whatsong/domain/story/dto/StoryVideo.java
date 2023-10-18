package dy.whatsong.domain.story.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoryVideo {
    private String videoId;
    private String title;
    private String channelName;
    private String thumbnailUrl;
}
