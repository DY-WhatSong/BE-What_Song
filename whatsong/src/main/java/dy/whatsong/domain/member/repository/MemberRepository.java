package dy.whatsong.domain.member.repository;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.entity.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberSeq(Long memberSeq);

    Optional<Member> findByOauthId(String oauthId);

    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findByOauthIdAndEmail(String oauthId, String email);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndSocialType(String email, SocialType socialType);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.refreshToken = :refreshToken WHERE m.oauthId = :oauthId AND m.email = :email")
    int updateRefreshToken(@Param("oauthId") String oauthId, @Param("email") String email, @Param("refreshToken") String refreshToken);

    Optional<String> findRefreshTokenByOauthIdAndEmail(String oauthId, String email);

}