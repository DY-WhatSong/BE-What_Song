package dy.whatsong.domain.member.dto;

import dy.whatsong.global.dto.page.PageRes;
import lombok.Getter;

import java.util.List;


public record FollowCurrentDTO(PageRes<FollowingListDTO> followingListDTOs, PageRes<FollowerListDTO> followerListDTOs, FollowCount followCount) {
}
