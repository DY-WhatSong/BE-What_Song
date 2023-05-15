package dy.whatsong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaAuditing
public class WhatsongApplication {

	@PostConstruct
	public void init(){
		System.out.println(">>> Server Open");
	}

	public static void main(String[] args) {
		SpringApplication.run(WhatsongApplication.class, args);
	}

}
