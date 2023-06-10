package dy.whatsong.domain.music.application.impl.check;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dy.whatsong.domain.music.application.service.check.MusicCheckService;
import dy.whatsong.domain.music.dto.MusicRoomDTO;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.music.entity.QMusicRoom;
import dy.whatsong.domain.music.entity.QMusicRoomMember;
import dy.whatsong.domain.music.repo.MusicRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MusicCheckServiceImpl implements MusicCheckService {

	private final MusicRoomRepository musicRoomRepository;

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public MusicRoom getInfoMRBySeq(Long musicRoomSeq) {
		Optional<MusicRoom> findMR = musicRoomRepository.findById(musicRoomSeq);
		return findMR.orElse(null);
	}


	@Override
	public List<MusicRoomDTO> getInfoListRoom() {
//		QMusicRoom qMusicRoom
//		jpaQueryFactory.selectFrom()
		return null;
	}
}
