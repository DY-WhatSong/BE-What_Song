package dy.whatsong.domain.member.application.service;

import dy.whatsong.domain.member.dto.MemberRequestDTO;
import org.springframework.http.ResponseEntity;

public interface MemberDetailService {
	ResponseEntity<?> memberFriendRequest(MemberRequestDTO.FriendsApply friendsApplyDTO);

	boolean isOwnerAlreadyFriendsRequest(Long ownerSeq, Long requestMemberSeq);

	ResponseEntity<?> memberSearchOnFriendsList(MemberRequestDTO.Search searchDTO);

	ResponseEntity<?> memberUnfollowRequest(MemberRequestDTO.FriendsApply friendsApplyDTO);
}
