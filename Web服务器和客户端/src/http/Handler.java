package http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


/**
 *  Class <em>HttpServer</em> is a class representing a simple HTTP Server
 *  
 *  @author 史嘉辉
 */
public class Handler implements Runnable{
	/**
	 * default HTTP listening port is port 80
	 */
	private static final int PORT = 80;
	
	/**
	 * default root dir
	 */
	private static final String ROOT_DIR = "D:\\www\\root";
	
	/**
	 * Allow a maximum buffer size of 8192 bytes
	 */
	private static int buffer_size = 8192;
	/**
	 * Request is stored in a byte array
	 */
	private byte[] buffer;
	/**
	 * client socket
	 */
	private Socket socket = null;
	/**
	 * Output Stream to the socket
	 */
	BufferedOutputStream ostream = null;
	/**
	 * Input Stream from the socket
	 */
	BufferedInputStream istream = null;
	/**
	 * StringBuffer storing the response
	 */
	private byte[] response = null;
	/**
	 * StringBuffer storing the header
	 */
	private StringBuffer header = null;
	/**
	 * String to represent the Carriage Return and Line Feed character sequence.
	 */
	private static final String CRLF = "\r\n";
	
	/**
	 * HttpServer constructor
	 */
	public Handler(Socket soc){
		this.socket = soc;
		buffer = new byte[buffer_size];
		header = new StringBuffer();
	}
	
	/**
	 * Get the response header.
	 */
	private String getHeader() {
		return header.toString();
	}

	/**
	 * Get the server's response.
	 */
	private String getResponse() {
		return response.toString();
	}
	/*
	 * 识别HTML和JPEG文件
	 */
	private String getMIME(String path){
		String[] strpath = path.split("\\.");
		String choose = strpath[1];
		String MIME=null;
		switch(choose){
			case "html" :
				MIME= "text/html";
				break;
			case "htm" : 
				MIME = "text/html";
				break;
			case "jpg" : 
				MIME = "image/jpg";
				break;
			case "jpeg" : 
				MIME = "image/jpg";
				break;
			default : 
				MIME = "text/plain";
				break;
				
		}
		return MIME;
	}
	/**
	 * Major Methods for Handling Request
	 */
	public void service(){
		
			try {
				ostream = new BufferedOutputStream(socket.getOutputStream());
				istream = new BufferedInputStream(socket.getInputStream());
				
				processRequestHeader();
				String headerStr = header.toString();
				
				//
				if(headerStr.startsWith("GET")){
					/**
					 * 
					 */
					String[] strs = headerStr.split("\r\n");
					String[] strs2 = strs[0].split(" ");
					String url = strs2[1];
					System.out.println(url);
					
					/**
					 * get file Content-Length
					 */
					url = url.replace("/", "\\");
					url = ROOT_DIR + url;
					System.out.println(url);
					int length = 0;
					byte[] b;
					if(existFileAddress(url)){
						length = getFileLength(url);
						/**
						 * Read server files and put them in byte arrays 
						 */
						b = new byte[length+1];
						File file = new File(url);
						BufferedInputStream bis ;
						FileInputStream fis = new FileInputStream(file);
						bis = new BufferedInputStream(fis);
						bis.read(b, 0, length);
						bis.close();
						
						/**
						 * 返回200报文头
						 */
						Date date = new Date();
						String httpDatagramres = "HTTP/1.1 200 OK"+CRLF
										 +"Server: MyHttpServer/1.0"+CRLF
										 +"Date: "+date.toString()+CRLF
										 +"Content-Type: "+getMIME(url)+CRLF
										 +"Content-Length: " + length+CRLF+CRLF;
						buffer = httpDatagramres.getBytes();
						ostream.write(buffer,0,httpDatagramres.length());
						ostream.flush();
						/**
						 * 返回文件的字节流
						 */					
						ostream.write(b,0,length);
						ostream.flush();
						System.out.println("包数："+httpDatagramres.length());
					}else{
						/**
						 * 返回404报文
						 */
						Date date = new Date();
						String httpDatagramres = "HTTP/1.1 404 Not Found"+CRLF
										 +"Server: MyHttpServer/1.0"+CRLF
										 +"Date: "+date.toString()+CRLF
										 +"Connection: close"+CRLF+CRLF;
						buffer = httpDatagramres.getBytes();
						ostream.write(buffer,0,httpDatagramres.length());
						ostream.flush();
					}

				}else if(headerStr.startsWith("PUT")){
					/**
					 * get request url
					 */
					String url = getUrl();
					/**
					 * Gets the byte length and saves it to the response byte array
					 * 得到
					 */
					int length = Integer.parseInt(getKey("Content-Length"));
					System.out.println("收到文件\n长度："+length);
					response = new byte[length+1];
					istream.read(response, 0, length);
					/**
					 * 写到文件中
					 */
					BufferedOutputStream bos ;
					File file = new File(ROOT_DIR+url);
					FileOutputStream fos = new FileOutputStream(file);
					bos = new BufferedOutputStream(fos);
					bos.write(response, 0, length);
					bos.close();
					//判断文件是否保存成功
					if(existFileAddress(ROOT_DIR+url)){
						/**
						 * 返回201报文头
						 */
						Date date = new Date();
						String httpDatagramres = "HTTP/1.1 201 Created"+CRLF
												+"Server: MyHttpServer/1.0"+CRLF
												+"Date: "+date.toString()+CRLF+CRLF;
						buffer = httpDatagramres.getBytes();
						ostream.write(buffer,0,httpDatagramres.length());
						ostream.flush();
					}else{
						/**
						 * 返回400报文头
						 */
						Date date = new Date();
						String httpDatagramres = "HTTP/1.1 400 Bad Request"+CRLF
												+"Server: MyHttpServer/1.0"+CRLF
												+"Date: "+date.toString()+CRLF
												+"Connection: close"+CRLF+CRLF;
						buffer = httpDatagramres.getBytes();
						ostream.write(buffer,0,httpDatagramres.length());
						ostream.flush();
					}
					this.socket.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				//关闭socket，否则使用火狐或者EDGE浏览器浏览html文件，在加载图片的时候会阻塞
				if(socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
	}
	/*
	 * 判断文件存在否
	 */
	public static Boolean existFileAddress(String fileAddress){
		File file = new File(fileAddress);
		if(file.exists()){
			return  true;
		}
		return false;
	}
	/**
	 * 得到文件长度
	 */
	public static int getFileLength(String filePath){
		File file = new File(filePath);
		return (int) file.length();
	}

	/*
	 * 处理请求头
	 */
	private void processRequestHeader() throws IOException{
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
	/*
	 *  KEY
	 */
	public String getKey(String key){
		String value = null;
		String[] rows = getHeader().split("\n");
		int i;
		for(i=0;i<rows.length;i++){
			if(rows[i].startsWith(key+":")){
				String[] strs = rows[i].split(": ");
				value = strs[1];
			}
		}		
		return value;
	}
	/**
	 *  URL 
	 * @return
	 */
	public String getUrl(){
		String[] strs = getHeader().split("/r/n");
		String[] strs2 = strs[0].split(" ");
		return strs2[1].replace("/", "\\");	
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.service();
	}
}
