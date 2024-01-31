package dy.whatsong.domain.healthcheck.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckAPI {

    @GetMapping("/healthcheck")
    public ResponseEntity<?> healthcheckServer() {
        return new ResponseEntity<>("Ping pololopongpongpong", HttpStatus.OK);
    }
}
