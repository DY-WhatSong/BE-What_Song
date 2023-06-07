package dy.whatsong.domain.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendsState {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long friendsSeq;

	private Long ownerSeq;

	private Long targetSeq;
}
