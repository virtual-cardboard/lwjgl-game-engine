package context.input.networking.packet;

import static context.input.networking.packet.RequestMethod.GET;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpRequestModel {

	private final String urlPath;
	private final RequestMethod requestMethod;
	private final String data;

	public HttpRequestModel(String urlPath, String data) {
		this(urlPath, GET, data);
	}

	public HttpRequestModel(String urlPath, RequestMethod requestMethod, String data) {
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
			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
			writer.write(data);
			writer.close();
			httpConn.getOutputStream().close();

			InputStream responseStream = httpConn.getResponseCode() / 100 == 2
					? httpConn.getInputStream()
					: httpConn.getErrorStream();
			responseStream.read(null);
			Scanner scanner = new Scanner(responseStream).useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next().getBytes() : null;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
