package dy.whatsong.domain.member.application.service.check;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemberDetailCheckService {
    List<Member> friendsListByOwnerSeq(Long ownerSeq);
}
