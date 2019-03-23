package ex01;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileSeverThreadPool {
	ServerSocket serverSocket;
	private final int PORT = 2021; // 端口

	ExecutorService executorService; // 线程池
	final int POOL_SIZE = 4; // 单个处理器线程池工作线程数目

	public FileSeverThreadPool() throws IOException {
		serverSocket = new ServerSocket(PORT); // 创建服务器端套接字

		// 创建线程池
		// Runtime的availableProcessors()方法返回当前系统可用处理器的数目
		// 由JVM根据系统的情况来决定线程的数量
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
		System.out.println("服务器启动。");
	}

	public static void main(String[] args) throws IOException {
		new FileSeverThreadPool().servic(); // 启动服务
	}

	/**
	 * service implements
	 */
	public void servic() {
		Socket socket = null;
		while (true) {
			try {
				socket = serverSocket.accept(); // 等待用户连接
				executorService.execute(new Handler(socket)); // 把执行交给线程池来维护
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
