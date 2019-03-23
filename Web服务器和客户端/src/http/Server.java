package http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * @author 史嘉辉
 */



public class Server {
	ServerSocket serverSocket;
	private final int PORT = 80;
	ExecutorService executorService;
	final int POOL_SIZE = 4; 

	public Server() throws IOException {
		serverSocket = new ServerSocket(PORT); 
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * POOL_SIZE);
		System.out.println("服务器已启动");
	}

	public static void main(String[] args) throws IOException {
		new Server().servic(); 
	}

	/**
	 * service implements
	 */
	public void servic() {
		Socket socket = null;
		while (true) {
			try {
				socket = serverSocket.accept(); 
				executorService.execute(new Handler(socket)); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
