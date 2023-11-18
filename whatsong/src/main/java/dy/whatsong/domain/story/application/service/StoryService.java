package dy.whatsong.domain.story.application.service;

import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.application.service.check.MemberDetailCheckService;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.story.dto.StoryListInfo;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class StoryService {
    private final StoryRepository storyRepository;

    private final MemberCheckService memberCheckService;

    private final MemberDetailCheckService memberDetailCheckService;

    private final RedisTemplate<String, List<Story>> redisTemplate;

    @Transactional
    public Story memberPostStory(final StoryPostReq storyPostReq) {
        LocalDateTime postTime = LocalDateTime.now();
        System.out.println("POST TIME :" + postTime);
        Long memberSeq = storyPostReq.getMemberSeq();
        Member memberInfoBySeq = memberCheckService.getInfoByMemberSeq(memberSeq);
        Story postedStory = storyRepository.save(
                Story.builder()
                        .id(memberSeq + ":" + UUID.randomUUID())
                        .memberStory(
                                MemberDto.MemberStory.builder()
                                        .memberSeq(memberInfoBySeq.getMemberSeq())
                                        .nickname(memberInfoBySeq.getNickname())
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
        postedHistory(memberSeq, postedStory);
        return postedStory;
    }

    private void postedHistory(Long memberSeq, Story postedStory) {
        String storyKey = "story:" + memberSeq.toString();
        ValueOperations<String, List<Story>> op = redisTemplate.opsForValue();
        List<Story> storyList = op.get(storyKey);
        if (storyList == null) storyList = new ArrayList<>();
        storyList.add(postedStory);
        op.set(storyKey, storyList);
    }


    public LinkedList<StoryListInfo> getFriendsStoryByList(final Long memberSeq) {
        ValueOperations<String, List<Story>> op = redisTemplate.opsForValue();
        List<Story> ownStoryList = op.get("story:" + memberSeq);

        LinkedList<StoryListInfo> listInfos = new LinkedList<>();
        if (ownStoryList != null) {
            String nickname = memberCheckService.getInfoByMemberSeq(memberSeq).getNickname();
            StoryListInfo myStoryInfo = new StoryListInfo(nickname, ownStoryList);
            listInfos.add(myStoryInfo);
        }

        List<Member> members = memberDetailCheckService.friendsListByOwnerSeq(memberSeq);
        for (Member m : members) {
            String storyKey = String.format("story:%s", m.getMemberSeq());
            List<Story> memberStory = op.get(storyKey);
            if (memberStory != null) {
                listInfos.add(new StoryListInfo(m.getNickname(), memberStory));
            }
        }
        /*members.stream()
                .map(m -> {
                    String storyKey = String.format("story:%s", m.getMemberSeq());
                    *//*List<Story> memberStory= op.get(storyKey);
                    System.out.println(memberStory);
                    return getStoryFor24Hours(memberStory);*//*
                    return op.get(storyKey);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());*/
        return listInfos;
    }

    private List<Story> getStoryFor24Hours(List<Story> memberStory) {
        /*LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        return memberStory.stream()
                .filter(story -> story.getPostTime().isAfter(twentyFourHoursAgo))
                .collect(Collectors.toList());*/
        return null;
    }

}
