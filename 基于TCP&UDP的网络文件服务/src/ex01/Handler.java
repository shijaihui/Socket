package ex01;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Handler implements Runnable {

	private Socket socket;
	BufferedReader br;
	BufferedWriter bw;
	PrintWriter pw;

	private static final String HOST = "127.0.0.1"; // IP address
	final int UDP_PORT = 2020; // UDP port
	private static final int SENDSIZE = 1024; // The number of bytes for each transport

	DatagramSocket socket2; // 客户端DatagramSocket
	SocketAddress socketAddres;

	public Handler(Socket socket) {

		this.socket = socket;

	}

	// 初始化输入输出流对象方法
	public void initStream() throws IOException {
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		pw = new PrintWriter(bw, true);
	}

	String paths = null;// 实时文件夹路径
	String path = "D:\\web";// 默认路径

	// 处理输入的命令
	Cd cmd = new Cd();

	// 读取文件夹
	public ArrayList<String> readfiles(String path) {
		ArrayList<String> files = new ArrayList<String>();
		File file = new File(path);
		File[] tempList = file.listFiles();
		if (tempList != null) {
			for (int i = 0; i < tempList.length; i++) {
				if (tempList[i].isDirectory()) {
					// System.out.println("<dir> " + tempList[i] + " " + tempList[i].length());
					files.add(("|<dir>  " + tempList[i] + "  " + tempList[i].length() + "|"));
				}
				if (tempList[i].isFile()) {
					// System.out.println("<file> " + tempList[i] + " " + tempList[i].length());
					files.add(("|<file>  " + tempList[i] + "  " + tempList[i].length() + "|"));
				}
			}
			return files;
		} else {

			return files;
		}
	}

	public void run() { // 执行的内容
		try {
			System.out.println("新连接，连接地址：" + socket.getInetAddress() + "：" + socket.getPort()); // 客户端信息
			initStream(); // 初始化输入输出流对象
			String info = null;
			System.out.println("当前路径>" + path);
			ArrayList<String> ok = readfiles(path);
			pw.println(socket.getInetAddress() + "：" + socket.getPort() + ">连接成功");
			int i = 1;
			paths = path;
			// 服务器端口
			int remotePort = socket.getPort();

			// gg
			while ((info = br.readLine()) != null) {
				System.out.println(info);
				ArrayList<String> ok1 = readfiles(paths);
				// 目录列表
				if (info.equals("ls")) {

					pw.println(ok1);

				}

				// 输入cd命令
				else if (info.startsWith("cd")) {
					// 获得命令中的目录
					String cm = cmd.cdcmd(info);
					if (cm.equals("null")) {
						pw.println("unkonw cmd");
					}
					// 返回上一级
					if (cm.equals("..")) {
						// 进入前一个目录
						ArrayList<String> ok3 = readfiles(path);
						if (ok3.toString() == "[]") {
							pw.println("unkonwn dir");
						} else {
							paths = path;
							pw.println("return > ok");
						}
					}
					// 进入指定文件夹
					else {
						paths = path + "\\" + cm;
						ArrayList<String> ok2 = readfiles(paths);
						if (ok2.toString() == "[]") {
							pw.println("no file");
						} else {
							pw.println(cm + " > ok");
						}
					}
				}

				// 输入get命令
				else if (info.startsWith("get")) {
					String cm2;
					cm2 = cmd.cdcmd(info);
					if (cm2.equals("null"))
						pw.println("unkonwn cmd");
					else {
						String p = paths + "\\" + cm2;// 需要传输的文件lu'jing
						File file = new File(p);// 创建文件对象

						if (file.exists()) {
							pw.println("正在接收文件:" + cm2);
							// 发送文件
							sendFile(file);
						} else {
							pw.println("unknown file");
						}
					}
				}

				// 退出
				else if (info.equals("bye")) {
					break;
				}

				// 输入错误命令
				else {
					pw.println("unkonwn cmd");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 发送文件 sendFile（）
	 */
	private void sendFile(File file) throws SocketException, IOException, InterruptedException {

		socket2 = new DatagramSocket();
		socketAddres = new InetSocketAddress(HOST, UDP_PORT);
		DatagramPacket dp;

		byte[] sendInfo = new byte[SENDSIZE];
		dp = new DatagramPacket(sendInfo, sendInfo.length, socketAddres);// 新建DatagramPacket对象

		BufferedInputStream bfdIS = new BufferedInputStream(new FileInputStream(file));// 文件流

		pw.println(file.length());// 发送数据长度

		byte[] sendDataByte = new byte[1024];// 创建数据包，存放拆分的文件
		int read = 0;// 判断数据包是否发送完毕
		System.out.println("开始发送文件");
		int i = 1;
		while (true) {
			if (bfdIS != null) {
				read = bfdIS.read(sendDataByte);
			}
			if (read == -1) {
				break;
			}
			dp = new DatagramPacket(sendDataByte, sendDataByte.length,
					(Inet4Address) Inet4Address.getByName("localhost"), UDP_PORT);// 创建传输流
			socket2.send(dp);// 发送
			TimeUnit.MICROSECONDS.sleep(1000);// 限制传输速度
			System.out.println("包数：" + i);
			i++;
		}
		System.out.println("发送完毕");
		socket2.close();
		bfdIS.close();
		System.out.println("关闭流");
	}
}
