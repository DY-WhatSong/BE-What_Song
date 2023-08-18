package dy.whatsong.domain.music.application.service.check;

import dy.whatsong.domain.music.dto.MusicRoomDTO;
import dy.whatsong.domain.music.dto.response.RoomResponseDTO;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.music.entity.MusicRoomMember;

import java.util.List;

public interface MusicCheckService {
	MusicRoom getInfoMRBySeq(Long musicRoomSeq);

	List<RoomResponseDTO.BasicRseponse> getInfoListRoom();

	boolean getInfoRoomLimit(Long memberSeq);

	RoomResponseDTO.BasicRseponse getRoomBasicInfoBySeq(Long roomSeq);

	List<RoomResponseDTO.BasicRseponse> makeListResponseRoomDTO(List<MusicRoomMember> musicRoomMembers);
}
