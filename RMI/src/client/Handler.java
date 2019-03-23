package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import meetingThrow.AddMeetingException;
import meetingThrow.BadInputException;
import meetingThrow.LoginFailedException;
import rface.MeetingInterface;
import server.Meeting;
import server.MeetingServer;

/**
 * 处理用户各种操作
 * 
 * @author 史嘉辉
 *  2018-21-12
 */
public class Handler {
	private MeetingInterface service;
	private SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
	private String username;
	private String password;

	public Handler(MeetingInterface service) {
		this.service = service;
	}

	/**
	 * 接受用户的输入并处理之
	 *
	 * @param startArgs
	 *            程序的启动参数
	 * @throws RemoteException
	 */
	public void process(String[] startArgs) throws RemoteException {

		username = startArgs[3];
		password = startArgs[4];

		//String op = null;// 如果用户直接带登录或注册，需要判断一下是否带有除用户名密码以外的参数
		

		String method = startArgs[2];
		String cmd = "";

		
			if (method.equals("register")) {
				boolean success = handleRegister();
				if (!success) {
					return;
				}
			} else if (method.equals("login")) {
				boolean success = handleLogin();
				if (!success) {
					return;
				}
			} else {
				System.out.println("无法识别启动参数\n"
						+ "请输入：[hostAddress] [portNumber] 'login'or'register' [username] [password] 以空格隔开");
				return;
			}
			if (startArgs.length >= 6) {// 带操作参数启动客户端，则处理跟在密码后的参数
				for (int i = 5; i < startArgs.length; i++) {
					cmd = cmd + startArgs[i] + " ";
				}
				System.out.println(cmd);
				try {
					startRepl2(cmd);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LoginFailedException e) {
					System.out.println(e.getMessage());
				}

			}
		

		try {
			handleHelp();
			startRepl();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LoginFailedException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 用户登录后，根据菜单输入参数值，执行此函数
	 *
	 * @throws IOException
	 * @throws LoginFailedException
	 */
	private void startRepl() throws IOException, LoginFailedException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String line;
		do {
			System.out.print("--> ");
			line = in.readLine();

			String[] cmd = line.split(" ");
			switch (cmd[0]) {
			case "add":
				handleAdd(cmd);
				break;
			case "query":
				handleQuery(cmd);
				break;
			case "delete":
				handleDelete(cmd);
				break;
			case "clear":
				handleClear();
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

	/**
	 * 如果用户启动客户端时，直接输入操作参数，则执行此函数 arg 启动客户端时，跟在密码后面的参数
	 * 
	 * @throws IOException
	 * @throws LoginFailedException
	 */
	private void startRepl2(String arg) throws IOException, LoginFailedException {
		String line = arg;
		String[] cmd = line.split(" ");
		switch (cmd[0]) {
		case "add":
			handleAdd(cmd);
			break;
		case "query":
			handleQuery(cmd);
			break;
		case "delete":
			handleDelete(cmd);
			break;
		case "clear":
			handleClear();
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

	}

	/*
	 * 重新打印一遍菜单
	 */
	private void handleHelp() {
		System.out.println("RMI Menu:\n" + "1. add\n"
				+ "        add [title] [otherusername] [start] [end]\n" + "2. delete\n" + "        delete [meetingId]\n"
				+ "3. clear\n" + "        no args\n" + "4. query\n" + "        query [start] [end]\n" + "5. help\n"
				+ "        no args\n" + "6. quit\n" + "        no args\n" + "输入日期格式: Year-Month-Day-hour:minute\n"
				+ "\nInput an operation:");
	}

	/**
	 * 添加一个会议
	 *
	 * @param args={add,
	 *            [title], [otherUserName], [start], [end]}
	 * @throws RemoteException
	 */
	private void handleAdd(String[] args) throws RemoteException, LoginFailedException {
		try {
			Meeting meeting = new Meeting(0, username, args[2], args[1], dateParser.parse(args[3]),
					dateParser.parse(args[4]));
			service.add(meeting, username, password);
			System.out.println("添加成功");
		} catch (AddMeetingException e) {
			System.out.println("当前会议与已有会议冲突，冲突会议已在下方列出");
			printMeetings(e.getConfilctMeetings());
		} catch (BadInputException e) {
			System.out.println(e.getMessage());
		} catch (ParseException e) {
			System.out.println("时间格式错误，请按照如下格式输入： 2018-12-21-00:00");
		}
	}
	/*
	 * 清除该用户的所有会议
	 * 
	 * @param args
	 */

	private void handleClear() throws RemoteException, LoginFailedException {
		service.clear(username, password);
		System.out.println("清除成功");
	}

	/*
	 * 删除该用户的一个会议
	 * 
	 * @param args
	 */
	private void handleDelete(String[] args) throws RemoteException, LoginFailedException {
		service.delete(Integer.parseInt(args[1]), username, password);
		System.out.println("删除成功");
	}

	/*
	 * 查询会议
	 * 
	 * @param args
	 */
	private void handleQuery(String[] args) throws RemoteException, LoginFailedException {
		List<Meeting> meetings = null;
		try {
			meetings = service.query(dateParser.parse(args[1]), dateParser.parse(args[2]), username, password);
		} catch (ParseException e) {
			System.out.println("时间格式不正确，正确的格式如下： 2018-12-21-00:00");
		}
		System.out.println("查询成功");
		printMeetings(meetings);
	}

	/**
	 * 打印出所需的会议
	 *
	 * @param meetings
	 */
	private void printMeetings(List<Meeting> meetings) {

		int length = meetings.size();
		System.out.println("========================================");
		System.out.println("共发现" + length + "个会议:");
		for (Meeting meeting : meetings) {
			System.out.println("----------------------------------------");
			System.out.println("会议号码----" + meeting.getId());
			System.out.println("会议标题----" + meeting.getTitle());
			System.out.println("开始时间----" + meeting.getStart());
			System.out.println("结束时间----" + meeting.getEnd());
			System.out.println("发起用户----" + meeting.getCreator());
			System.out.println("参与用户----" + meeting.getOtherUser());

		}
		System.out.println("========================================");
	}

	/**
	 * 用户注册
	 *
	 * @return 用户名被注册或输入参数错误时返回 false
	 * @throws RemoteException
	 */
	private boolean handleRegister() throws RemoteException {
		boolean success = service.register(username, password);
		if (success) {
			System.out.println("注册成功");
		} else {
			System.out.println("注册失败，请重新输入");
		}
		return success;
	}

	/**
	 * 用户登陆，匹配用户名和密码。
	 *
	 * @return 密码不匹配或用户不存在时返回 false
	 * @throws RemoteException
	 */
	private boolean handleLogin() throws RemoteException {
		boolean success = service.checkUser(username, password);
		if (!success) {
			System.out.println("登陆失败，请检查您的用户名和密码");
		} else {
			System.out.println("登陆成功");
		}
		return success;
	}

}
