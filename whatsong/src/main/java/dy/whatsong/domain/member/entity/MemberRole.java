package dy.whatsong.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN"),
	GUEST("ROLE_GUEST");

	private final String key;
}
