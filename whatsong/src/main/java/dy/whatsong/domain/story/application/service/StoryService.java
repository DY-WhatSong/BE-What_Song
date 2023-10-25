package dy.whatsong.domain.story.application.service;

import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.application.service.check.MemberDetailCheckService;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.story.dto.req.FriendsStoryReq;
import dy.whatsong.domain.story.dto.req.StoryPostReq;
import dy.whatsong.domain.story.entity.Story;
import dy.whatsong.domain.story.repo.StoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Log4j2
public class StoryService {
    private final StoryRepository storyRepository;

    private final MemberCheckService memberCheckService;

    private final MemberDetailCheckService memberDetailCheckService;

    private final RedisTemplate<String,List<Story>> redisTemplate;

    @Transactional
    public Story memberPostStory(final StoryPostReq storyPostReq){
        LocalDateTime postTime = LocalDateTime.now();
        System.out.println("POST TIME :"+postTime);
        Member memberInfoBySeq = memberCheckService.getInfoByMemberSeq(storyPostReq.getMemberSeq());
        String memberNickName = memberInfoBySeq.getNickname();
        String storyKeyValue = "story:" + memberNickName + postTime;
        Story postedStory = storyRepository.save(
                Story.builder()
                        .id(storyKeyValue + UUID.randomUUID())
                        .memberStory(
                                MemberDto.MemberStory.builder()
                                        .memberSeq(memberInfoBySeq.getMemberSeq())
                                        .nickname(memberNickName)
                                        .imgURL(memberInfoBySeq.getImgURL())
                                        .build()
                        )
                        .start(storyPostReq.getStart())
                        .end(storyPostReq.getEnd())
                        .postTime(postTime)
                        .img_url(storyPostReq.getImg_url())
                        .storyVideo(storyPostReq.getStoryVideoReq().reqToSaveTarget())
                        .build()
        );
        postedHistory(storyKeyValue,postedStory);
        return postedStory;
    }

    private void postedHistory(String storyKey,Story postedStory){
        ValueOperations<String, List<Story>> postedStoryList = redisTemplate.opsForValue();
        List<Story> postedStoryHistory = postedStoryList.get(storyKey);
        if (postedStoryHistory == null) postedStoryHistory = new ArrayList<>();
        postedStoryHistory.add(postedStory);
        postedStoryList.set(storyKey,postedStoryHistory);
    }

    public LinkedList<Story> getFriendsStoryByList(final FriendsStoryReq friendsStoryReq){
        List<Member> members = memberDetailCheckService.friendsListByOwnerSeq(friendsStoryReq.getOwnerSeq());
        for (Member m: members){
            m.getNickname()
            storyRepository.findAllById();
        }
    }
}
