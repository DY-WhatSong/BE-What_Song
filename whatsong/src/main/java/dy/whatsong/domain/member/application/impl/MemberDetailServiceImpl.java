package dy.whatsong.domain.member.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dy.whatsong.domain.member.application.service.MemberDetailService;
import dy.whatsong.domain.member.dto.MemberRequestDTO;
import dy.whatsong.domain.member.entity.FriendsState;
import dy.whatsong.domain.member.entity.QFriendsState;
import dy.whatsong.domain.member.repo.FriendsStateRepository;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@EssentialServiceLayer
@RequiredArgsConstructor
@Log4j2
public class MemberDetailServiceImpl implements MemberDetailService {

	private final JPAQueryFactory jpaQueryFactory;

	private final FriendsStateRepository friendsStateRepository;

	@Override
	@Transactional
	public ResponseEntity<?> memberFriendRequest(MemberRequestDTO.FriendsApply friendsApplyDTO) {

		boolean alreadyFriends = isAlreadyFriends(friendsApplyDTO.getOwnerSeq(),friendsApplyDTO.getTargetSeq());
		if (alreadyFriends){
			return new ResponseEntity<>("Already Friends", HttpStatus.OK);
		}
		FriendsState friendsNew = FriendsState.builder()
				.ownerSeq(friendsApplyDTO.getOwnerSeq())
				.targetSeq(friendsApplyDTO.getTargetSeq())
				.build();
		friendsStateRepository.save(friendsNew);
		return new ResponseEntity<>(friendsNew,HttpStatus.OK);
	}

	@Override
	public boolean isAlreadyFriends(Long ownerSeq,Long requestMemberSeq){
		QFriendsState qfs=QFriendsState.friendsState;

		BooleanExpression friendConditon = qfs.ownerSeq.eq(ownerSeq)
				.and(qfs.targetSeq.eq(requestMemberSeq));

		return jpaQueryFactory.selectOne()
				.from(qfs)
				.where(friendConditon)
				.fetchFirst() != null;
	}
}
