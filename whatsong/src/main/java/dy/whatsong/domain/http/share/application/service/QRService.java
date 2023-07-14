package dy.whatsong.domain.http.share.application.service;

import com.google.zxing.WriterException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface QRService {
	ResponseEntity<?> generateQrCodeToPNG(String url) throws WriterException, IOException;
}
