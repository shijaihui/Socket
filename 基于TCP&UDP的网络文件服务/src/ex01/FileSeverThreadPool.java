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
	private final int PORT = 2021; // �˿�

	ExecutorService executorService; // �̳߳�
	final int POOL_SIZE = 4; // �����������̳߳ع����߳���Ŀ

	public FileSeverThreadPool() throws IOException {
		serverSocket = new ServerSocket(PORT); // �������������׽���

		// �����̳߳�
		// Runtime��availableProcessors()�������ص�ǰϵͳ���ô���������Ŀ
		// ��JVM����ϵͳ������������̵߳�����
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
		System.out.println("������������");
	}

	public static void main(String[] args) throws IOException {
		new FileSeverThreadPool().servic(); // ��������
	}

	/**
	 * service implements
	 */
	public void servic() {
		Socket socket = null;
		while (true) {
			try {
				socket = serverSocket.accept(); // �ȴ��û�����
				executorService.execute(new Handler(socket)); // ��ִ�н����̳߳���ά��
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
