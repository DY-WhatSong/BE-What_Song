package dy.whatsong.domain.member.application.impl.cache;

import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;


@EssentialServiceLayer
@RequiredArgsConstructor
public class MemberCacheServiceImpl implements MemberCacheService {

    private final MemberCheckService memberCheckService;

    @Override
    public ResponseEntity<?> putMemberInCacheIfEmpty(Long memberSeq) {
        Member findBySeqMember = memberCheckService.getInfoByMemberSeq(memberSeq);
        return null;
    }
}
