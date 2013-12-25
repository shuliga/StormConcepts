import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * User: yshuliga
 * Date: 21.11.13 18:16
 */
public class HttpTestClient {


	private static final int COUNT = 10000;
	private static final long DELAY = 5;
	final static String[] words = new String[] {"nathan", "mike", "jackson", "golda", "bertels"};
	final static Random rand = new Random();
	public static final String CHARSET_NAME = "UTF-8";

	public static void main(String[] args) {
		final String host = args.length > 0 ? args[0] : "elevterius";
		StopWatch stopWatch = new StopWatch();
		int code = 200;
		for (int i = 0 ; i < COUNT; i++){
			stopWatch.start();
			final int _i = i;
			new Thread(){
				@Override
				public void run() {
					int code = doRequest("http://" + host + ":9998/spout", "{\"personId\" : 100, \"comment\"  : \"" + getWord() + "\" , \"date\": \"21-12-2013\"}");
					if (code != 200) {
						System.out.println("Unexpected return code: " + code + ", requests sent: " + _i);
					}
				}
			}.start();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
			stopWatch.reset();
		}
	}

	private static int doRequest(String url, String json){
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)new URL(url).openConnection();
		} catch (IOException e) {
			return -1;
		}
		connection.setDoOutput(true); // Triggers POST.
		connection.setRequestProperty("Accept-Charset", CHARSET_NAME);
		connection.setRequestProperty("Content-Type", "application/json");
		OutputStream output = null;
		InputStream response = null;
		BufferedReader reader = null;
		int status = -1;
		try {
			output = connection.getOutputStream();
			output.write(json.getBytes(CHARSET_NAME));
			response = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(response, CHARSET_NAME));
			for (String line; (line = reader.readLine()) != null;) {
			}
			status = connection.getResponseCode();
		} catch (IOException e) {
			status = 530;
			System.out.println(e.getMessage());
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(response);
			connection.disconnect();
		}
		return status;
	}

	private static String getWord() {
		return words[rand.nextInt(words.length)];
	}
}
