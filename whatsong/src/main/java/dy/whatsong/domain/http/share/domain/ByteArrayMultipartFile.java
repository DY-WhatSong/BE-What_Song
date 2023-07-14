package dy.whatsong.domain.http.share.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ByteArrayMultipartFile implements MultipartFile {
	private final byte[] fileContent;
	private final String filename;

	public ByteArrayMultipartFile(byte[] fileContent, String filename) {
		this.fileContent = fileContent;
		this.filename = filename;
	}
	@Override
	public String getName() {
		return filename;
	}

	@Override
	public String getOriginalFilename() {
		return filename;
	}

	@Override
	public String getContentType() {
		return "image/png";
	}

	@Override
	public boolean isEmpty() {
		return fileContent == null || fileContent.length == 0;
	}

	@Override
	public long getSize() {
		return fileContent.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return fileContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(fileContent);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		new FileOutputStream(dest).write(fileContent);
	}
}
