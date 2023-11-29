package dy.whatsong.domain.member.dto;

import lombok.Getter;

import java.util.List;


public record FollowCurrentDTO(List<FollowingListDTO> followingListDTOs, List<FollowerListDTO> followerListDTOs, Integer followingCount, Integer followerCount) {
}
