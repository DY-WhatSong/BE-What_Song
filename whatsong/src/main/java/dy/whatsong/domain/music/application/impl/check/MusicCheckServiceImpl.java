package dy.whatsong.domain.music.application.impl.check;

import dy.whatsong.domain.music.application.service.check.MusicCheckService;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.music.repo.MusicRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MusicCheckServiceImpl implements MusicCheckService {

	private final MusicRoomRepository musicRoomRepository;

	@Override
	public MusicRoom getInfoMRBySeq(Long musicRoomSeq) {
		Optional<MusicRoom> findMR = musicRoomRepository.findById(musicRoomSeq);
		return findMR.orElse(null);

	}
}
