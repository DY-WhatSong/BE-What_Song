package dy.whatsong.domain.story.dto.req;

import dy.whatsong.domain.youtube.dto.StoryVideoReq;
import lombok.Getter;

@Getter
public class StoryPostReq {
    private Long memberSeq;
    private String img_url;
    private String start;
    private String end;
    private StoryVideoReq storyVideoReq;
}
