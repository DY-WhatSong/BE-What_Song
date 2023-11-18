package dy.whatsong.domain.youtube.dto;

import dy.whatsong.domain.story.dto.StoryVideo;
import lombok.Getter;

@Getter
public class StoryVideoReq {
    private String videoId;
    private String title;
    private String channelName;
    private String thumbnailUrl;

    public StoryVideo reqToSaveTarget(){
        return StoryVideo.builder()
                .videoId(videoId)
                .channelName(channelName)
                .title(title)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
