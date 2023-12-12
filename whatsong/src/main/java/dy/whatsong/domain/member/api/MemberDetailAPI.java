package dy.whatsong.domain.member.api;

import dy.whatsong.domain.member.application.service.MemberDetailService;
import dy.whatsong.domain.member.dto.FollowCurrentDTO;
import dy.whatsong.domain.member.dto.MemberRequestDTO;
import dy.whatsong.global.annotation.EssentialController;
import dy.whatsong.global.dto.page.PageReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@EssentialController
@RequiredArgsConstructor
public class MemberDetailAPI {
    private final MemberDetailService memberDetailService;

    @PostMapping("/friends/search")
    public ResponseEntity<?> searchOnMemberList(@RequestBody MemberRequestDTO.Search searchDTO) {
        return memberDetailService.memberSearchOnFriendsList(searchDTO);
    }

    @PostMapping("/friends/apply")
    public ResponseEntity<?> applyOnMember(@RequestBody MemberRequestDTO.FriendsApply friendsApplyDTO) {
        return memberDetailService.memberFriendRequest(friendsApplyDTO);
    }

    @GetMapping("/friends")
    public ResponseEntity<?> getMemberFriendsList(@RequestParam("ownerSeq") Long ownerSeq) {
        return new ResponseEntity<>(memberDetailService.getMemberFriendsList(ownerSeq), HttpStatus.OK);
    }

    @DeleteMapping("/friends/apply")
    public ResponseEntity<?> applyUnfollowOnMEmber(@RequestBody MemberRequestDTO.FriendsApply friendsApply) {
        return memberDetailService.memberUnfollowRequest(friendsApply);
    }

    @GetMapping("/friends/following")
    public ResponseEntity<?> getMemberFriendsFollowList(@RequestParam("ownerSeq") Long ownerSeq,
                                                   @Valid PageReq pageReq) {
        FollowCurrentDTO followCurrentDTO = memberDetailService.findByFollowingList(ownerSeq, pageReq.page(), pageReq.size());
        return new ResponseEntity<>(followCurrentDTO, HttpStatus.OK);
    }

    @GetMapping("/friends/follower")
    public ResponseEntity<?> getMemberFriendsFollowerList(@RequestParam("ownerSeq") Long ownerSeq,
                                                          @Valid PageReq pageReq){
        FollowCurrentDTO followCurrentDTO = memberDetailService.findByFollowerList(ownerSeq, pageReq.page(), pageReq.size());
        return new ResponseEntity<>(followCurrentDTO, HttpStatus.OK);
    }
}
