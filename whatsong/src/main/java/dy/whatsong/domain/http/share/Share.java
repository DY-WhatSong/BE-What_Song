package dy.whatsong.domain.http.share;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Share {

	@Value("${music_room.share.prefix}")
	public static String URL_PREFIX;
}
