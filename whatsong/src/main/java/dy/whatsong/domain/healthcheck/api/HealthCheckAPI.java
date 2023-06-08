package dy.whatsong.domain.healthcheck.api;

import dy.whatsong.global.annotation.EssentialController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@EssentialController
public class HealthCheckAPI {

	@GetMapping("/healthcheck")
	public ResponseEntity<?> healthcheckServer(){
		return new ResponseEntity<>("Ping pololopongpongpong", HttpStatus.OK);
	}
}
