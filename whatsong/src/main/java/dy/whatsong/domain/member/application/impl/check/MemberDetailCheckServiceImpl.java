package dy.whatsong.domain.member.application.impl.check;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.application.service.check.MemberDetailCheckService;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.entity.QFriendsState;
import dy.whatsong.domain.member.repo.FriendsStateRepository;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;


@EssentialServiceLayer
@RequiredArgsConstructor
@Log4j2
public class MemberDetailCheckServiceImpl implements MemberDetailCheckService {

    private final JPAQueryFactory jpaQueryFactory;

    private final MemberCheckService memberCheckService;

    @Override
    public List<Member> friendsListByOwnerSeq(Long ownerSeq) {
        QFriendsState qfs=QFriendsState.friendsState;
        return jpaQueryFactory.select(qfs.targetSeq)
                .from(qfs)
                .where(qfs.ownerSeq.eq(ownerSeq))
                .fetch() //Target Sequence List
                .stream() // seq -> find Member Entity use Stream
                .map(memberCheckService::getInfoByMemberSeq)
                .collect(Collectors.toList());
    }
}
