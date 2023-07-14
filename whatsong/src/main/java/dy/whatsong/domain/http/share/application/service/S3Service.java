package dy.whatsong.domain.http.share.application.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
	String upload(MultipartFile multipartFile, String dirName) throws IOException;
}
