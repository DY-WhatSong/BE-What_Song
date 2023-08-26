package dy.whatsong.domain.member.application.service.cache;


import org.springframework.http.ResponseEntity;

public interface MemberCacheService {
    ResponseEntity<?> putMemberInCacheIfEmpty(Long memberSeq);
}
