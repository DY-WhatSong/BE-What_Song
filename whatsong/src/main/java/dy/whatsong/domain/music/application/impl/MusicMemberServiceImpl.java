package dy.whatsong.domain.music.application.impl;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.application.service.MusicMemberService;
import dy.whatsong.domain.music.entity.MusicRoom;
import dy.whatsong.domain.music.entity.MusicRoomMember;
import dy.whatsong.domain.music.repo.MusicMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class MusicMemberServiceImpl implements MusicMemberService {

	private final MusicMemberRepository musicMemberRepository;

	@Override
	public void createdRoomDetails(MusicRoom musicRoom, Member member) {
		musicMemberRepository.save(
				MusicRoomMember.builder()
						.musicRoom(musicRoom)
						.member(member)
						.ownerSeq(member.getMemberSeq())
						.build()
		);
	}

	@Override
	public void deletedRoomDetails(MusicRoom musicRoom) {
		Optional<MusicRoomMember> findMRM = musicMemberRepository.findByMusicRoom(musicRoom);
		musicMemberRepository.delete(findMRM.get());
	}
}
