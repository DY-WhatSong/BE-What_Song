package dy.whatsong.domain.music.application.service.check;

import dy.whatsong.domain.music.entity.MusicRoom;

public interface MusicCheckService {
	MusicRoom getInfoMRBySeq(Long musicRoomSeq);
}
