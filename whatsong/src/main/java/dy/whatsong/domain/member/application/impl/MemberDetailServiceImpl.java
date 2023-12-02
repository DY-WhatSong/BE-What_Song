package dy.whatsong.domain.member.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dy.whatsong.domain.member.application.service.MemberDetailService;
import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.dto.*;
import dy.whatsong.domain.member.entity.FriendsState;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.entity.QFriendsState;
import dy.whatsong.domain.member.entity.QMember;
import dy.whatsong.domain.member.repo.FriendsStateRepository;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import dy.whatsong.global.dto.page.CustomPageable;
import dy.whatsong.global.dto.page.PageRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@EssentialServiceLayer
@RequiredArgsConstructor
@Log4j2
public class MemberDetailServiceImpl implements MemberDetailService {

    private final JPAQueryFactory jpaQueryFactory;

    private final FriendsStateRepository friendsStateRepository;

    private final MemberCheckService memberCheckService;

    @Override
    @Transactional
    public ResponseEntity<?> memberFriendRequest(MemberRequestDTO.FriendsApply friendsApplyDTO) {
        Long ownerSeq = friendsApplyDTO.getOwnerSeq();
        Long targetSeq = friendsApplyDTO.getTargetSeq();

        if (isOwnerAlreadyFriendsRequest(ownerSeq, targetSeq)) {
            if (isAlreadyBothFriends(friendsApplyDTO.getOwnerSeq(), friendsApplyDTO.getTargetSeq())) {
                return new ResponseEntity<>("Both Follow", HttpStatus.OK);
            }
            return new ResponseEntity<>("Already Follow", HttpStatus.OK);
        }
        FriendsState friendsNew = FriendsState.builder()
                .ownerSeq(friendsApplyDTO.getOwnerSeq())
                .targetSeq(friendsApplyDTO.getTargetSeq())
                .build();
        friendsStateRepository.save(friendsNew);
        return new ResponseEntity<>(friendsNew, HttpStatus.OK);
    }

    public boolean isAlreadyBothFriends(Long ownerSeq, Long targetSeq) {
        QFriendsState qFriendsState = QFriendsState.friendsState;
        return jpaQueryFactory.selectFrom(qFriendsState)
                .where(qFriendsState.ownerSeq.eq(ownerSeq).and(qFriendsState.targetSeq.eq(targetSeq)
                        .and(qFriendsState.targetSeq.eq(ownerSeq).and(qFriendsState.ownerSeq.eq(targetSeq))
                        )))
                .fetchFirst() != null;
    }

    @Override
    public boolean isOwnerAlreadyFriendsRequest(Long ownerSeq, Long requestMemberSeq) {
        QFriendsState qfs = QFriendsState.friendsState;
        System.out.println("ownerSeq:" + ownerSeq);
        BooleanExpression friendConditon = qfs.ownerSeq.eq(ownerSeq)
                .and(qfs.targetSeq.eq(requestMemberSeq));

        return jpaQueryFactory.selectOne()
                .from(qfs)
                .where(friendConditon)
                .fetchFirst() != null;
    }

    public boolean isOwnerAlreadyFriends(Long ownerSeq, Long targetSeq) {
        Optional<List<FriendsState>> findFS = friendsStateRepository.findByOwnerSeq(ownerSeq);
        for (FriendsState fs : findFS.get()) {
            if (fs.getTargetSeq().equals(targetSeq)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<?> memberSearchOnFriendsList(MemberRequestDTO.Search searchDTO) {
        QMember qMember = QMember.member;
        BooleanExpression friendsCandidate = qMember.memberSeq.ne(searchDTO.getOwnerSeq())
                .and(qMember.nickname.startsWith(searchDTO.getTargetName()));
        List<Member> fetchResult = jpaQueryFactory.selectFrom(qMember)
                .where(friendsCandidate)
                .fetch();

        List<FriendsSearchListDTO> searchList = fetchResult.stream().map(member -> {
            return new FriendsSearchListDTO(member.getMemberSeq(), member.getEmail(), member.getNickname(), member.getImgURL(), isOwnerAlreadyFriendsRequest(searchDTO.getOwnerSeq(), member.getMemberSeq()));
        }).collect(Collectors.toList());
        return new ResponseEntity<>(searchList, HttpStatus.OK);
    }

    @Override
    public List<FriendsState> getMemberFriendsList(Long ownerSeq) {
        return friendsStateRepository.findByOwnerSeq(ownerSeq).get();
    }

    @Override
    @Transactional
    public ResponseEntity<?> memberUnfollowRequest(MemberRequestDTO.FriendsApply friendsApplyDTO) {
        QFriendsState qf = QFriendsState.friendsState;
        Long o = friendsApplyDTO.getOwnerSeq();
        Long t = friendsApplyDTO.getTargetSeq();
        BooleanExpression findCondition = qf.ownerSeq.eq(o).
                and(qf.targetSeq.eq(t));

        /*jpaQueryFactory.delete(qf)
                .where(findCondition);*/
        List<FriendsState> friendsStates = friendsStateRepository.findByOwnerSeq(o).get();
        for (FriendsState f : friendsStates) {
            if (f.getTargetSeq().equals(t)) {
                friendsStateRepository.delete(f);
            }
        }

        return new ResponseEntity<>(o + "unfollowed" + t, HttpStatus.OK);
    }

    @Override
    public FollowCurrentDTO findByFollowList(Long ownerSeq, int page, int size) {
        QFriendsState qf = QFriendsState.friendsState;
        Pageable pageable = CustomPageable.of(page, size, Sort.unsorted());

        Page<FollowingListDTO> followingListPage = getFollowListByQueryDslPaging(qf.targetSeq, qf.ownerSeq.eq(ownerSeq), FollowingListDTO::new, ownerSeq, pageable);
        Page<FollowerListDTO> followerListPage = getFollowListByQueryDslPaging(qf.ownerSeq, qf.targetSeq.eq(ownerSeq), FollowerListDTO::new, ownerSeq, pageable);

        return new FollowCurrentDTO(new PageRes<>(followingListPage), new PageRes<>(followerListPage),findByFollowCount(ownerSeq));
    }

    private <T> Page<T> getFollowListByQueryDslPaging(NumberExpression<Long> selection, BooleanExpression condition, BiFunction<Long, FriendsStateMemberDTO, T> constructor, Long ownerSeq, Pageable pageable) {
        List<T> content = jpaQueryFactory.select(selection)
                .from(QFriendsState.friendsState)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(memberCheckService::getInfoByMemberSeq)
                .map(member -> constructor.apply(member.getMemberSeq(), followingOrFollowListMemberInfo(member, ownerSeq)))
                .collect(Collectors.toList());

        // 총 개수를 가져오기 위해 별도의 쿼리를 수행합니다.
        long total = jpaQueryFactory.selectFrom(QFriendsState.friendsState)
                .where(condition)
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    private FriendsStateMemberDTO followingOrFollowListMemberInfo(Member member, Long ownerSeq){
        return new FriendsStateMemberDTO(member.getEmail(), member.getNickname(), member.getImgURL(),
                            isOwnerAlreadyFriendsRequest(ownerSeq, member.getMemberSeq()));
    }

    private FollowCount findByFollowCount(Long ownerSeq){
        QFriendsState qf = QFriendsState.friendsState;
        int followingSize = jpaQueryFactory.selectFrom(qf)
                .where(qf.ownerSeq.eq(ownerSeq))
                .fetch().size();
        int followerSize = jpaQueryFactory.selectFrom(qf)
                .where(qf.targetSeq.eq(ownerSeq))
                .fetch().size();

        return new FollowCount(followingSize,followerSize);
    }
}
