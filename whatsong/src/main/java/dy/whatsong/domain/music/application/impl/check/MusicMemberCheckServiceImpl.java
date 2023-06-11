package dy.whatsong.domain.music.application.impl.check;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.application.service.check.MusicMemberCheckService;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.music.entity.MusicRoomMember;
import dy.whatsong.domain.music.entity.QMusicRoomMember;
import dy.whatsong.domain.music.repo.MusicMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class MusicMemberCheckServiceImpl implements MusicMemberCheckService {

	private final MusicMemberRepository musicMemberRepository;

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<MusicRoomMember> getInfoMRMListByMember(Member member) {
		QMusicRoomMember qmrm=QMusicRoomMember.musicRoomMember;
		List<MusicRoomMember> fetchResult = jpaQueryFactory.selectFrom(qmrm)
				.where(qmrm.member.eq(member))
				.fetch();
		return fetchResult;
	}

	@Override
	public MusicRoomMember getInfoMRMByRoom(MusicRoom musicRoom){
		return musicMemberRepository.findByMusicRoom(musicRoom).get();
	}
}