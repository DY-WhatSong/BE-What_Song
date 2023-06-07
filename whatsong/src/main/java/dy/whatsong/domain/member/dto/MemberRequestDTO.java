package dy.whatsong.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberRequestDTO {

	@Getter
	public static class FriendsApply{
		private Long ownerSeq;
		private Long targetSeq;
	}
}
