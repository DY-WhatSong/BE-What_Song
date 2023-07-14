package dy.whatsong.domain.music.application.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import dy.whatsong.domain.http.share.QR;
import dy.whatsong.domain.music.application.service.QRService;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@EssentialServiceLayer
@RequiredArgsConstructor
public class QRServiceImpl implements QRService {

	private final QR qr;

	@Override
	public ResponseEntity<?> generateQrCodeToPNG(final String url) throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, qr.width, qr.height);
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			MatrixToImageWriter.writeToStream(matrix, "PNG", out);
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_PNG)
					.body(out.toByteArray());
		}
	}
}
