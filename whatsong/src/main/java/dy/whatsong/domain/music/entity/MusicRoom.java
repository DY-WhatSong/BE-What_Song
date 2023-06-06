package dy.whatsong.domain.music.entity;

import dy.whatsong.domain.music.dto.request.MusicRequestDTO;
import dy.whatsong.domain.music.dto.response.RoomResponseDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long musicRoomSeq;

	private String roomName;

	private String roomCode;

	private String category;

	@Enumerated(EnumType.STRING)
	private AccessAuth accessAuth;

	@OneToMany(mappedBy = "musicRoom")
	private List<MusicRoomMember> musicRoomMembers;

	public MusicRoom changeElements(MusicRequestDTO.ChangeInfo changeInfoDTO){
		this.accessAuth=changeInfoDTO.getAccessAuth();
		this.category=changeInfoDTO.getCategory();
		this.roomName=changeInfoDTO.getName();
		return this;
	}

	public RoomResponseDTO.Have toHaveRoomDTO(){
		return RoomResponseDTO.Have.builder()
				.musicRoomSeq(musicRoomSeq)
				.roomCode(roomCode)
				.roomName(roomName)
				.accessAuth(accessAuth)
				.category(category)
				.build();
	}

	public RoomResponseDTO.Change toChangeInfoDTO(){
		return RoomResponseDTO.Change.builder()
				.musicRoomSeq(musicRoomSeq)
				.roomCode(roomCode)
				.accessAuth(accessAuth)
				.category(category)
				.roomName(roomName)
				.build();
	}
}
