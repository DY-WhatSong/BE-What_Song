package dy.whatsong.domain.story.dto.req;

import dy.whatsong.domain.story.dto.PostTime;
import lombok.Getter;

@Getter
public class FriendsStoryReq {
    private Long ownerSeq;
    private PostTime postTime;
}
