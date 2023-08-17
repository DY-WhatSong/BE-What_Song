package dy.whatsong.domain.member.repository;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.entity.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberSeq(Long memberSeq);

    @Query("select m from Member m where m.oauthId = :oauthId")
    Optional<Member> findByOauthId(@Param("oauthId") String oauthId);

    @Query("select m from Member m where m.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);

    Optional<Member> findByEmailAndSocialType(String email, SocialType socialType);


    Optional<Member> findByRefreshToken(String refreshToken);
}
