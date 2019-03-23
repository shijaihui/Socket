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

	DatagramSocket socket2; // �ͻ���DatagramSocket
	SocketAddress socketAddres;

	public Handler(Socket socket) {

		this.socket = socket;

	}

	// ��ʼ��������������󷽷�
	public void initStream() throws IOException {
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		pw = new PrintWriter(bw, true);
	}

	String paths = null;// ʵʱ�ļ���·��
	String path = "D:\\web";// Ĭ��·��

	// �������������
	Cd cmd = new Cd();

	// ��ȡ�ļ���
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

	public void run() { // ִ�е�����
		try {
			System.out.println("�����ӣ����ӵ�ַ��" + socket.getInetAddress() + "��" + socket.getPort()); // �ͻ�����Ϣ
			initStream(); // ��ʼ���������������
			String info = null;
			System.out.println("��ǰ·��>" + path);
			ArrayList<String> ok = readfiles(path);
			pw.println(socket.getInetAddress() + "��" + socket.getPort() + ">���ӳɹ�");
			int i = 1;
			paths = path;
			// �������˿�
			int remotePort = socket.getPort();

			// gg
			while ((info = br.readLine()) != null) {
				System.out.println(info);
				ArrayList<String> ok1 = readfiles(paths);
				// Ŀ¼�б�
				if (info.equals("ls")) {

					pw.println(ok1);

				}

				// ����cd����
				else if (info.startsWith("cd")) {
					// ��������е�Ŀ¼
					String cm = cmd.cdcmd(info);
					if (cm.equals("null")) {
						pw.println("unkonw cmd");
					}
					// ������һ��
					if (cm.equals("..")) {
						// ����ǰһ��Ŀ¼
						ArrayList<String> ok3 = readfiles(path);
						if (ok3.toString() == "[]") {
							pw.println("unkonwn dir");
						} else {
							paths = path;
							pw.println("return > ok");
						}
					}
					// ����ָ���ļ���
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

				// ����get����
				else if (info.startsWith("get")) {
					String cm2;
					cm2 = cmd.cdcmd(info);
					if (cm2.equals("null"))
						pw.println("unkonwn cmd");
					else {
						String p = paths + "\\" + cm2;// ��Ҫ������ļ�lu'jing
						File file = new File(p);// �����ļ�����

						if (file.exists()) {
							pw.println("���ڽ����ļ�:" + cm2);
							// �����ļ�
							sendFile(file);
						} else {
							pw.println("unknown file");
						}
					}
				}

				// �˳�
				else if (info.equals("bye")) {
					break;
				}

				// �����������
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
	 * �����ļ� sendFile����
	 */
	private void sendFile(File file) throws SocketException, IOException, InterruptedException {

		socket2 = new DatagramSocket();
		socketAddres = new InetSocketAddress(HOST, UDP_PORT);
		DatagramPacket dp;

		byte[] sendInfo = new byte[SENDSIZE];
		dp = new DatagramPacket(sendInfo, sendInfo.length, socketAddres);// �½�DatagramPacket����

		BufferedInputStream bfdIS = new BufferedInputStream(new FileInputStream(file));// �ļ���

		pw.println(file.length());// �������ݳ���

		byte[] sendDataByte = new byte[1024];// �������ݰ�����Ų�ֵ��ļ�
		int read = 0;// �ж����ݰ��Ƿ������
		System.out.println("��ʼ�����ļ�");
		int i = 1;
		while (true) {
			if (bfdIS != null) {
				read = bfdIS.read(sendDataByte);
			}
			if (read == -1) {
				break;
			}
			dp = new DatagramPacket(sendDataByte, sendDataByte.length,
					(Inet4Address) Inet4Address.getByName("localhost"), UDP_PORT);// ����������
			socket2.send(dp);// ����
			TimeUnit.MICROSECONDS.sleep(1000);// ���ƴ����ٶ�
			System.out.println("������" + i);
			i++;
		}
		System.out.println("�������");
		socket2.close();
		bfdIS.close();
		System.out.println("�ر���");
	}
}
