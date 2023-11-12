package dy.whatsong.domain.story.api;


import dy.whatsong.domain.story.application.service.StoryService;
import dy.whatsong.domain.story.dto.StoryListInfo;
import dy.whatsong.domain.story.dto.req.StoryPostReq;
import dy.whatsong.domain.story.entity.Story;
import dy.whatsong.global.annotation.EssentialController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;

@EssentialController
@RequiredArgsConstructor
public class StoryAPI {
    private final StoryService storyService;

    @PostMapping("/story")
    public ResponseEntity<?> postStoryByMember(@RequestBody StoryPostReq storyPostReq) {
        Story postedStory = storyService.memberPostStory(storyPostReq);
        return new ResponseEntity<>(postedStory, HttpStatus.OK);
    }

    @GetMapping("/story")
    public ResponseEntity<?> getRedis(@RequestParam Long memberSeq) {
        LinkedList<StoryListInfo> friendsStories = storyService.getFriendsStoryByList(memberSeq);
        return new ResponseEntity<>(friendsStories, HttpStatus.OK);
    }
}
