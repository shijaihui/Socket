package ex01;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * tcp , client side
 * 
 * @author ʷ�λ�
 *
 */
public class FileClient {
	static final int tcp_PORT = 2021; // ���Ӷ˿�
	static final String HOST = "127.0.0.1"; // ���ӵ�ַ
	Socket socket = new Socket();
	private static final int SENDSIZE = 1024; // Data size for each sending
	private static final int udp_PORT = 2020; // UDP port
	DatagramSocket socket2; // �ͻ���DatagramSocket

	public FileClient() throws UnknownHostException, IOException, SocketException {

		socket = new Socket();
		socket.connect(new InetSocketAddress(HOST, tcp_PORT));
	}

	public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
		new FileClient().send();
	}

	/**
	 * send implements
	 */
	public void send() {
		try {
			// �ͻ���������������������Ϣ
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			// �ͻ��������������շ�������Ϣ
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// װ�����������ʱˢ��
			PrintWriter pw = new PrintWriter(bw, true);
			// �����û���Ϣ
			Scanner in = new Scanner(System.in);
			String msg = null;
			// ������������ص���Ϣ
			System.out.println(br.readLine());
			Cd cmd = new Cd();
			while ((msg = in.nextLine()) != null) {
				pw.println(msg); // ���͸���������
				String ls = br.readLine();
				System.out.println(ls);// ������յ�����Ϣ

				if (ls.startsWith("���ڽ����ļ�:")) {

					DatagramPacket dp = new DatagramPacket(new byte[SENDSIZE], SENDSIZE);
					socket2 = new DatagramSocket(udp_PORT);// Create UDP socket
					byte[] info = new byte[SENDSIZE];
					String filename = cmd.cdcmd(msg);
					FileOutputStream fos = new FileOutputStream(new File(("D:\\temp\\") + filename));// �ļ�����
					System.out.println("�ȴ��ļ�����");
					long fileLength = Long.parseLong(br.readLine());// �����ļ��ĳ���
					System.out.println("�յ��ļ�����");
					int count = (int) (fileLength / SENDSIZE) + ((fileLength % SENDSIZE) == 0 ? 0 : 1);// �ж���Ҫ���ն��ٸ���
					System.out.println("׼�������ļ�");
					while ((count--) > 0) {
						socket2.receive(dp);
						info = dp.getData();
						fos.write(info, 0, dp.getLength());
						// fos.flush();
					}
					System.out.println("���ڽ����ļ�");

					socket2.close();
					System.out.println("�ر���");
					fos.close();
					System.out.println("�ļ��������");
				}

				if (msg.equals("bye")) {
					break; // �˳�
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != socket) {
				try {
					socket.close(); // �Ͽ�����
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}