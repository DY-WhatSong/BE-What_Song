package dy.whatsong.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberRequestDTO {

	@Getter
	public static class FriendsApply{
		private Long ownerSeq;
		private Long targetSeq;
	}

	@Getter
	public static class Search{
		private Long ownerSeq;
		private String targetName;
	}

	@Getter
	public static class OnlyRefreshToken{
		private String refreshToken;
	}
}
