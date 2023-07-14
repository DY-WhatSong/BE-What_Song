package dy.whatsong.domain.http.share.application.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import dy.whatsong.domain.http.share.application.service.S3Service;
import dy.whatsong.domain.http.share.domain.ByteArrayMultipartFile;
import dy.whatsong.domain.http.share.domain.QR;
import dy.whatsong.domain.http.share.application.service.QRService;
import dy.whatsong.domain.http.share.domain.S3;
import dy.whatsong.domain.music.dto.response.RoomResponseDTO;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@EssentialServiceLayer
@RequiredArgsConstructor
public class QRServiceImpl implements QRService {

	private final QR qr;

	private final S3 s3;

	private final S3Service s3Service;

	@Override
	public ResponseEntity<?> generateQrCodeToPNG(final String url) throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, qr.width, qr.height);
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			MatrixToImageWriter.writeToStream(matrix, "PNG", out);

			MultipartFile multipartFile = new ByteArrayMultipartFile(out.toByteArray(), "qrCode.png");
			String s3UploadedUrl = s3Service.upload(multipartFile, s3.dirName);

			return new ResponseEntity<>(RoomResponseDTO
											.ShareResponse
											.builder()
											.basicUrl(url)
											.qrUrl(s3UploadedUrl)
											.build(), HttpStatus.OK);
		}
	}
}
