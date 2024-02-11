package dy.whatsong.domain.member.service.oauth.dto.res;

import dy.whatsong.domain.member.entity.Member;

public record MemberDetailRes(Long id, String oauthId, String nickname, String innerNickName, String imgUrl,
                              String email) {

    public MemberDetailRes(Member member) {
        this(member.getMemberSeq(), member.getOauthId(), member.getNickname(), member.getInnerNickname(), member.getImgURL(), member.getEmail());
    }
}
