package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import UserThrow.BadInputException;
import UserThrow.LoginFailedException;
import bean.User;
import rface.MessageInterface;
import server.Message;
import server.MessageServer;

/**
 * 处理用户的各种操作
 * 
 * @author 史嘉辉
 *  2018-21-28
 */
public class Handler {
	private MessageInterface service;
	private String username;
	private String password;

	public Handler(MessageInterface service) {
		this.service = service;
	}

	/**
	 * 用户登录后，根据菜单输入参数值，执行此函数
	 *
	 * @throws IOException
	 * @throws LoginFailedException
	 */
	void start() throws IOException, LoginFailedException {
		handleHelp();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String line;
		do {
			System.out.print(">>> ");
			line = in.readLine();

			String[] cmd = line.split(" ");
			switch (cmd[0]) {
			case "register":
				handleRegister(cmd);
				break;
			case "check":
				handleCheck(cmd);
				break;
			case "leave":
				handleLeave(cmd);
				break;
			case "show":
				handleShow();
				break;
			case "quit":
				break;
			case "help":
				handleHelp();
			case "":
				break;
			default:
				System.out.println("请输入正确的指令，例如：help");
			}
		} while (!line.equals("quit"));
	}
	
	/*
	 * 查看留言
	 * 
	 */
	private void handleCheck(String x[]) throws RemoteException {
		String username = x[1];
		String password = x[2];
		boolean success = service.checkUser(username, password);
		if(!success) {
			System.out.println("用户名密码错误，请重新输入！");
		}else {
			List<Message> messages = null;
			messages = service.checkmessages(username, password);		
			if(messages.equals(null)) {
				System.out.println("该用户没有留言！");
			}else {
				
				int length = messages.size();
				System.out.println("发现"+length+"条留言");
				System.out.println("============================================================");
				for(Message m : messages) {
					input(m);
				}
				System.out.println("============================================================");
			}
		}
	}
	
	/*
	 * 查看用户
	 */
	private void handleShow() throws RemoteException {
		List<User> users = service.showuser();
		int length = users.size();
		System.out.println("发现"+length+"个用户");
		System.out.println("---------------------------------------");
		int x = 1;
		for(User user : users) {
			System.out.println("用户"+x+": "+user.getName());
			x++;
		}
		System.out.println("---------------------------------------");
	}
	/*
	 * 发布留言
	 */
	private void handleLeave(String x[]) throws RemoteException {
		String username = x[1];
		String password = x[2];
		String receiver_user = x[3];
		String text = x[4];
		boolean s1 = service.checkUser(username, password);
		boolean s2 = service.checkUser(receiver_user);
		if(!s1) {
			System.out.println("用户名密码错误！");
		}
		if(!s2) {
			System.out.println("接收者不存在！");
		}
		if(s1&&s2) {
			boolean success = service.leavemessage(username, password, receiver_user, text);
			if(success) {
				System.out.println("留言成功！");
			}
			else {
				System.out.println("留言失败！");
			}
		}
	}

	/*
	 * 重新打印一遍菜单
	 */
	private void handleHelp() {
		System.out.println("RMI Menu:\n" + "1. register\n"
				+ "        register [username] [password]\n" + "2. leave\n" + "        leave [username] [password] [reciever_name] [message]\n"
				+ "3. showuser\n" + "        show\n" + "4. checkmessage\n" + "        check [username] [password]\n" + "5. help\n"
				+ "        no args\n" + "6. quit\n" + "        no args\n"
				+ "\nInput an operation:");
	}



	/**
	 * 用户注册
	 *
	 * @return 用户名被注册或输入参数错误时返回 false
	 * @throws RemoteException
	 */
	private boolean handleRegister(String[] x) throws RemoteException {
		String username = x[1];
		String password = x[2];
		boolean success = service.register(username, password);
		if (success) {
			System.out.println("注册成功！");
		} else {
			System.out.println("用户名被占用，请更换用户名");
		}
		return success;
	}


	/*
	 * 打印出留言
	 */
	private void input(Message message) {
		System.out.println("------------------------------------------------------------");
		System.out.println("留言作者" + message.getCreator());
		System.out.println("留言时间" + message.getTime());
		System.out.println("留言内容" + message.getMessage());
		// System.out.println("------------------------------------------------------------");
	}

}
