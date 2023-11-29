package dy.whatsong.domain.member.dto;

import lombok.Getter;

public record FollowingListDTO(Long memberSeq, FriendsStateMemberDTO friendsStateMemberDTO) {
}
