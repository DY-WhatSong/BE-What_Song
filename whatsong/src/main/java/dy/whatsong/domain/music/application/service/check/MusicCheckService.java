package dy.whatsong.domain.music.application.service.check;

import dy.whatsong.domain.music.dto.MusicRoomDTO;
import dy.whatsong.domain.music.dto.response.RoomResponseDTO;
import dy.whatsong.domain.music.entity.MusicRoom;

import java.util.List;

public interface MusicCheckService {
	MusicRoom getInfoMRBySeq(Long musicRoomSeq);

	List<RoomResponseDTO.Have> getInfoListRoom();
}
