package http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class <em>HttpClient</em> is a class representing a simple HTTP client.
 *
 * @author wben
 */

public class HttpClient {

	/**
	 * default HTTP port is port 80
	 */
	private static int port = 80;

	/**
	 * Allow a maximum buffer size of 8192 bytes
	 */
	private static int buffer_size = 8192;

	/**
	 * Response is stored in a byte array.
	 */
	private byte[] buffer;
	/**
	 * default root dir
	 */
	private static final String ROOT_DIR = "D:\\www\\test";
	/**
	 * My socket to the world.
	 */
	Socket socket = null;

	/**
	 * Default port is 80.
	 */
	private static final int PORT = 80;

	/**
	 * Output stream to the socket.
	 */
	BufferedOutputStream ostream = null;

	/**
	 * Input stream from the socket.
	 */
	BufferedInputStream istream = null;

	/**
	 * StringBuffer storing the header
	 */
	private StringBuffer header = null;

	/**
	 * StringBuffer storing the response.
	 */
	private byte[] response = null;

	/**
	 * String to represent the Carriage Return and Line Feed character sequence.
	 */
	static private String CRLF = "\r\n";

	/**
	 * HttpClient constructor;
	 */
	public HttpClient() {
		buffer = new byte[buffer_size];
		header = new StringBuffer();
	}

	/**
	 * <em>connect</em> connects to the input host on the default http port -- port
	 * 80. This function opens the socket and creates the input and output streams
	 * used for communication.
	 */
	public void connect(String host) throws Exception {

		/**
		 * Open my socket to the specified host at the default port.
		 */
		socket = new Socket(host, PORT);

		/**
		 * Create the output stream.
		 */
		ostream = new BufferedOutputStream(socket.getOutputStream());

		/**
		 * Create the input stream.
		 */
		istream = new BufferedInputStream(socket.getInputStream());
	}

	/**
	 * <em>processGetRequest</em> process the input GET request.
	 */
	public void processGetRequest(String request) throws Exception {
		/**
		 * Send the request to the server.
		 */
		request += CRLF + CRLF;
		buffer = request.getBytes();
		ostream.write(buffer, 0, request.length());
		ostream.flush();
		/**
		 * waiting for the response.
		 */
		processResponseHeader();
		processResponseBody();
	}

	/**
	 * <em>processPutRequest</em> process the input PUT request. Modified by 史嘉辉
	 * 2018-12-1
	 */
	public void processPutRequest(String request) throws Exception {
		/**
		 * GET Content-Length
		 */

		String[] spls = request.split(" ");

		String url = spls[1];
		url = url.replace("/", "\\");
		url = this.ROOT_DIR + url;
		int length = 0;
		if (FileAddressexist(url)) {
			length = getFileLength(url);
			response = new byte[length + 1];
			/**
			 * 
			 * 从客户端目录读出文件，并放入byte流
			 */
			File file = new File(url);

			BufferedInputStream bt;
			FileInputStream files = new FileInputStream(file);
			bt = new BufferedInputStream(files);
			bt.read(response, 0, length);
			bt.close();

			/**
			 * 向服务器发送请求
			 */
			request += CRLF + "Content-Length: " + length + CRLF + CRLF;

			buffer = request.getBytes();
			ostream.write(buffer, 0, request.length());
			ostream.flush();
			ostream.write(response, 0, length);
			ostream.flush();
			/**
			 * 等待 response.
			 */
		} else {
			System.out.println("文件不存在！");
		}

		processResponseHeader();
	}

	/**
	 * <em>processResponse</em> process the server response header.
	 * 
	 */
	public void processResponseHeader() throws Exception {
		int last = 0, c = 0;
		/**
		 * Process the header and add it to the header StringBuffer.
		 */
		boolean inHeader = true; // loop control
		while (inHeader && ((c = istream.read()) != -1)) {
			switch (c) {
			case '\r':
				break;
			case '\n':
				if (c == last) {
					inHeader = false;
					break;
				}
				last = c;
				header.append("\n");
				break;
			default:
				last = c;
				header.append((char) c);
			}
		}

	}

	/**
	 * <em>processResponse</em> process the server response body.
	 * 
	 */
	public void processResponseBody() throws Exception {
		int length = Integer.parseInt(getKey("Content-Length"));
		response = new byte[length + 1];
		istream.read(response, 0, length);
	}

	public void saveFileAS(String fileName) {
		BufferedOutputStream bos;
		File fileout = new File(this.ROOT_DIR + "\\" + fileName);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileout);
			bos = new BufferedOutputStream(fos);
			bos.write(response, 0, response.length - 1);
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Get the response header.
	 */
	public String getHeader() {
		return header.toString();
	}

	/**
	 * Get the server's response.
	 */
	public String getResponse() {
		return new String(response);
	}

	public String getKey(String key) {
		String value = "0";
		String[] rows = getHeader().split("\n");
		int i;
		for (i = 0; i < rows.length; i++) {
			if (rows[i].startsWith(key + ":")) {
				String[] strs = rows[i].split(": ");
				value = strs[1];
			}
		}

		return value;
	}

	/**
	 * Close all open connections -- sockets and streams.
	 */
	public void close() throws Exception {
		socket.close();
		istream.close();
		ostream.close();
	}

	/*
	 * 文件存在否
	 */
	public static Boolean FileAddressexist(String fileAddress) {
		File testFile = new File(fileAddress);
		if (testFile.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 文件长度几何
	 */
	public static int getFileLength(String filePath) {
		File file = new File(filePath);
		return (int) file.length();
	}
}
