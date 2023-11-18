package dy.whatsong.domain.member.application.service;

import dy.whatsong.domain.member.dto.MemberRequestDTO;
import dy.whatsong.domain.member.entity.FriendsState;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberDetailService {
	ResponseEntity<?> memberFriendRequest(MemberRequestDTO.FriendsApply friendsApplyDTO);

	boolean isOwnerAlreadyFriendsRequest(Long ownerSeq, Long requestMemberSeq);

	ResponseEntity<?> memberSearchOnFriendsList(MemberRequestDTO.Search searchDTO);

	List<FriendsState> getMemberFriendsList(Long ownerSeq);

	ResponseEntity<?> memberUnfollowRequest(MemberRequestDTO.FriendsApply friendsApplyDTO);
}
