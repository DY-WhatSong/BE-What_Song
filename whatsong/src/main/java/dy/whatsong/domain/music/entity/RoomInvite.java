package dy.whatsong.domain.music.entity;

import lombok.Getter;

@Getter
public class RoomInvite {
	public Long roomSeq;

	public String roomCode;

	public InviteType inviteType;

	public String inviter;
}
