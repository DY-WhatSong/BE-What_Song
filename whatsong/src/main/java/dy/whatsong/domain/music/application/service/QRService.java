package dy.whatsong.domain.music.application.service;

import com.google.zxing.WriterException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface QRService {
	ResponseEntity<?> generateQrCodeToPNG(String url) throws WriterException, IOException;
}
