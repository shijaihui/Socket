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
 * @author 史嘉辉
 *
 */
public class FileClient {
	static final int tcp_PORT = 2021; // 连接端口
	static final String HOST = "127.0.0.1"; // 连接地址
	Socket socket = new Socket();
	private static final int SENDSIZE = 1024; // Data size for each sending
	private static final int udp_PORT = 2020; // UDP port
	DatagramSocket socket2; // 客户端DatagramSocket

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
			// 客户端输出流，向服务器发消息
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			// 客户端输入流，接收服务器消息
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 装饰输出流，及时刷新
			PrintWriter pw = new PrintWriter(bw, true);
			// 接受用户信息
			Scanner in = new Scanner(System.in);
			String msg = null;
			// 输出服务器返回的消息
			System.out.println(br.readLine());
			Cd cmd = new Cd();
			while ((msg = in.nextLine()) != null) {
				pw.println(msg); // 发送给服务器端
				String ls = br.readLine();
				System.out.println(ls);// 输出接收到的信息

				if (ls.startsWith("正在接收文件:")) {

					DatagramPacket dp = new DatagramPacket(new byte[SENDSIZE], SENDSIZE);
					socket2 = new DatagramSocket(udp_PORT);// Create UDP socket
					byte[] info = new byte[SENDSIZE];
					String filename = cmd.cdcmd(msg);
					FileOutputStream fos = new FileOutputStream(new File(("D:\\temp\\") + filename));// 文件对象
					System.out.println("等待文件长度");
					long fileLength = Long.parseLong(br.readLine());// 接收文件的长度
					System.out.println("收到文件长度");
					int count = (int) (fileLength / SENDSIZE) + ((fileLength % SENDSIZE) == 0 ? 0 : 1);// 判断需要接收多少个包
					System.out.println("准备接收文件");
					while ((count--) > 0) {
						socket2.receive(dp);
						info = dp.getData();
						fos.write(info, 0, dp.getLength());
						// fos.flush();
					}
					System.out.println("正在接收文件");

					socket2.close();
					System.out.println("关闭流");
					fos.close();
					System.out.println("文件接收完毕");
				}

				if (msg.equals("bye")) {
					break; // 退出
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != socket) {
				try {
					socket.close(); // 断开连接
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}