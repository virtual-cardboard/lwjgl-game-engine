package context.input.networking.packet;

import static context.input.networking.packet.RequestMethod.GET;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class HttpRequestModel {

	private final String urlPath;
	private final RequestMethod requestMethod;
	private byte[] data;
	private final byte[] response = new byte[8192];

	public HttpRequestModel(byte[] data, String urlPath) {
		this(data, urlPath, GET);
	}

	public HttpRequestModel(byte[] data, String urlPath, RequestMethod requestMethod) {
		this.urlPath = urlPath;
		this.requestMethod = requestMethod;
		this.data = data;
	}

	public byte[] execute() {
		try {
			URL url = new URL(urlPath);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod(requestMethod.toString());

			httpConn.setDoOutput(true);
			httpConn.getOutputStream().write(data);
			httpConn.getOutputStream().close();

			InputStream responseStream = httpConn.getResponseCode() / 100 == 2
					? httpConn.getInputStream()
					: httpConn.getErrorStream();
			int numRead = responseStream.read(response);
			return Arrays.copyOf(response, numRead);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
