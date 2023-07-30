package dy.whatsong.domain.member.repository;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.entity.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberSeq(Long memberSeq);

    Optional<Member> findByOauthId(String oauthId);

    @Query("select m from Member m where m.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);

    Optional<Member> findByEmailAndSocialType(String email, SocialType socialType);


    @Query("UPDATE Member m SET m.refreshToken = ?3 WHERE m.oauthId = ?1 AND m.email = ?2")
    int updateRefreshToken(String oauthId, String email, String refreshToken);


}
