package dy.whatsong.domain.chat.entity;

import dy.whatsong.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatSeq;

	private String content;

	@ManyToOne
	private Member member;

	@ManyToOne
	private ChatRoom chatRoom;

}
