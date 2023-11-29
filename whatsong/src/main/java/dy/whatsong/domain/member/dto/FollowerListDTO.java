package dy.whatsong.domain.member.dto;

import lombok.Getter;

@Getter
public record FollowerListDTO(Long memberSeq, FriendsStateMemberDTO friendsStateMemberDTO) {
}
