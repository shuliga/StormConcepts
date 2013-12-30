package com.shl.poc.storm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.lang.time.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: yshuliga
 * Date: 21.11.13 15:25
 */
public class HttpServerQueue extends Thread {

	private static final int QUEUE_CAPACITY = 1000;
	public static final int TTW = 1000;
	public static AtomicInteger counter = new AtomicInteger(0);
	public volatile boolean stop = false;
	private StopWatch stopWatch = new StopWatch();
	private Timer timer;

	private static BlockingQueue<SpoutRequest> queue = new SynchronousQueue<SpoutRequest>();

	class Handler implements HttpHandler {

		public void handle(HttpExchange xchg) throws IOException {
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(xchg.getRequestBody(), "UTF-8"));
			StringBuilder requestStrBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null){
				requestStrBuilder.append(inputStr);
			}
			Gson gson = new GsonBuilder()
					.setDateFormat("dd-MM-yyyy")
					.create();
			SpoutRequest spoutRequest = gson. fromJson(requestStrBuilder.toString(), SpoutRequest.class);
			try {
				queue.put(spoutRequest);
				counter.incrementAndGet();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			xchg.sendResponseHeaders(200, 0);
			OutputStream os = xchg.getResponseBody();
			os.close();
		}
	}

	private HttpServer server;

	public HttpServerQueue(){
		try {
			server =  HttpServer.create(new InetSocketAddress(9998), 0);
			server.createContext("/spout", new Handler());
			server.start();
			System.out.println("HTTP Server started");
			stopWatch.start();
			timer = new Timer();
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					System.out.println(" Requests processed: " + counter.get() + ", queue RemainingCapacity: " + queue.remainingCapacity());
					stopWatch.reset();
					stopWatch.start();
				}
			}, 0, TTW);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void stopServer(){
		timer.cancel();
		server.stop(0);
	}

	public static class SpoutRequest {
		public int personId;
		public String comment;
		public Date date;
	}

	public static void main(String[] args) {
		HttpServerQueue server = new HttpServerQueue();
		server.start();
	}

	public static BlockingQueue<SpoutRequest> getQueue() {
		return queue;
	}
}
