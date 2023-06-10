package dy.whatsong.musicRoom;

import dy.whatsong.domain.music.application.service.MusicRoomService;
import dy.whatsong.domain.music.dto.request.MusicRequestDTO;
import dy.whatsong.domain.music.entity.AccessAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MusicRoomAPITest {

	@Autowired
	private MusicRoomService musicRoomService;

	private static final Long MEMBER_SEQ=12200L;

	private static final Long ROOM_SEQ=23967L;


	@Test
	public void JPA_사용시(){
		musicRoomService.requestPrivateRoomJPA(MEMBER_SEQ,ROOM_SEQ);
	}

	@Test
	public void QUERYDSL_사용시(){
		MusicRequestDTO.AccessRoom buildRoom = MusicRequestDTO.AccessRoom.
				builder()
				.memberSeq(MEMBER_SEQ)
				.roomSeq(ROOM_SEQ)
				.build();
		musicRoomService.ableAccessRoom(buildRoom);
	}

	@Test()
	public void 뮤직방_더미데이터(){
		for (int i=0;i<10;i++){
			musicRoomService.createMusicRoom(
			MusicRequestDTO.Create
					.builder()
					.memberSeq(MEMBER_SEQ)
					.accessAuth(AccessAuth.PRIVATE)
					.category("J-POP")
					.roomName("TEST"+i)
					.build()
			);
		}
	}
}
