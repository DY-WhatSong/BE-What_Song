package dy.whatsong.domain.http.share;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Getter
@Component
public class QR {

	@PostConstruct
	public void log(){
		System.out.println("width="+width);
	}

	@Value("${qr.width}")
	public int width;

	@Value("${qr.height}")
	public int height;
}
