package dy.whatsong.domain.music.application.impl.check;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.music.application.service.check.MusicMemberCheckService;
import dy.whatsong.domain.music.entity.MusicRoomMember;
import dy.whatsong.domain.music.repo.MusicMemberRepository;
import lombok.RequiredArgsConstructor;
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

	@Override
	public List<MusicRoomMember> getInfoMRMListByMember(Member member) {
		Optional<List<MusicRoomMember>> byMember = musicMemberRepository.findByMember(member);
		return byMember.get();
	}
}