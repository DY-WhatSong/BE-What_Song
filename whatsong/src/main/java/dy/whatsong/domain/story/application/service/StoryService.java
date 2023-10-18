package dy.whatsong.domain.story.application.service;

import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.story.dto.req.StoryPostReq;
import dy.whatsong.domain.story.entity.Story;
import dy.whatsong.domain.story.repo.StoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class StoryService {
    private final StoryRepository storyRepository;

    private final MemberCheckService memberCheckService;


    @Transactional
    public Story memberPostStory(final StoryPostReq storyPostReq){
        LocalDateTime postTime = LocalDateTime.now();
        Member memberInfoBySeq = memberCheckService.getInfoByMemberSeq(storyPostReq.getMemberSeq());
        String memberNickName = memberInfoBySeq.getNickname();
        return storyRepository.save(
                Story.builder()
                        .id("story:" + memberNickName + postTime + UUID.randomUUID())
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
    }

}
