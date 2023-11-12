package dy.whatsong.domain.story.dto;

import dy.whatsong.domain.story.entity.Story;
import lombok.Getter;

import java.util.List;

@Getter
public class StoryListInfo {
    private String memberName;
    private List<Story> stories;

    public StoryListInfo(String memberName, List<Story> stories) {
        this.memberName = memberName;
        this.stories = stories;
    }
}
