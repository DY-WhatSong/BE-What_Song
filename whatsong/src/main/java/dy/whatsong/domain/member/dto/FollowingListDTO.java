package dy.whatsong.domain.member.dto;

import lombok.Getter;

@Getter
public record FollowingListDTO(Long memberSeq, FriendsStateMemberDTO friendsStateMemberDTO) {
}
