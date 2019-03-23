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
 * �����û����ֲ���
 * 
 * @author ʷ�λ�
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
	 * �����û������벢����֮
	 *
	 * @param startArgs
	 *            �������������
	 * @throws RemoteException
	 */
	public void process(String[] startArgs) throws RemoteException {

		username = startArgs[3];
		password = startArgs[4];

		//String op = null;// ����û�ֱ�Ӵ���¼��ע�ᣬ��Ҫ�ж�һ���Ƿ���г��û�����������Ĳ���
		

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
				System.out.println("�޷�ʶ����������\n"
						+ "�����룺[hostAddress] [portNumber] 'login'or'register' [username] [password] �Կո����");
				return;
			}
			if (startArgs.length >= 6) {// ���������������ͻ��ˣ�������������Ĳ���
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
	 * �û���¼�󣬸��ݲ˵��������ֵ��ִ�д˺���
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
				System.out.println("��������ȷ��ָ����磺help");
			}
		} while (!line.equals("quit"));
	}

	/**
	 * ����û������ͻ���ʱ��ֱ�����������������ִ�д˺��� arg �����ͻ���ʱ�������������Ĳ���
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
			System.out.println("��������ȷ��ָ����磺help");
		}

	}

	/*
	 * ���´�ӡһ��˵�
	 */
	private void handleHelp() {
		System.out.println("RMI Menu:\n" + "1. add\n"
				+ "        add [title] [otherusername] [start] [end]\n" + "2. delete\n" + "        delete [meetingId]\n"
				+ "3. clear\n" + "        no args\n" + "4. query\n" + "        query [start] [end]\n" + "5. help\n"
				+ "        no args\n" + "6. quit\n" + "        no args\n" + "�������ڸ�ʽ: Year-Month-Day-hour:minute\n"
				+ "\nInput an operation:");
	}

	/**
	 * ���һ������
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
			System.out.println("��ӳɹ�");
		} catch (AddMeetingException e) {
			System.out.println("��ǰ���������л����ͻ����ͻ���������·��г�");
			printMeetings(e.getConfilctMeetings());
		} catch (BadInputException e) {
			System.out.println(e.getMessage());
		} catch (ParseException e) {
			System.out.println("ʱ���ʽ�����밴�����¸�ʽ���룺 2018-12-21-00:00");
		}
	}
	/*
	 * ������û������л���
	 * 
	 * @param args
	 */

	private void handleClear() throws RemoteException, LoginFailedException {
		service.clear(username, password);
		System.out.println("����ɹ�");
	}

	/*
	 * ɾ�����û���һ������
	 * 
	 * @param args
	 */
	private void handleDelete(String[] args) throws RemoteException, LoginFailedException {
		service.delete(Integer.parseInt(args[1]), username, password);
		System.out.println("ɾ���ɹ�");
	}

	/*
	 * ��ѯ����
	 * 
	 * @param args
	 */
	private void handleQuery(String[] args) throws RemoteException, LoginFailedException {
		List<Meeting> meetings = null;
		try {
			meetings = service.query(dateParser.parse(args[1]), dateParser.parse(args[2]), username, password);
		} catch (ParseException e) {
			System.out.println("ʱ���ʽ����ȷ����ȷ�ĸ�ʽ���£� 2018-12-21-00:00");
		}
		System.out.println("��ѯ�ɹ�");
		printMeetings(meetings);
	}

	/**
	 * ��ӡ������Ļ���
	 *
	 * @param meetings
	 */
	private void printMeetings(List<Meeting> meetings) {

		int length = meetings.size();
		System.out.println("========================================");
		System.out.println("������" + length + "������:");
		for (Meeting meeting : meetings) {
			System.out.println("----------------------------------------");
			System.out.println("�������----" + meeting.getId());
			System.out.println("�������----" + meeting.getTitle());
			System.out.println("��ʼʱ��----" + meeting.getStart());
			System.out.println("����ʱ��----" + meeting.getEnd());
			System.out.println("�����û�----" + meeting.getCreator());
			System.out.println("�����û�----" + meeting.getOtherUser());

		}
		System.out.println("========================================");
	}

	/**
	 * �û�ע��
	 *
	 * @return �û�����ע��������������ʱ���� false
	 * @throws RemoteException
	 */
	private boolean handleRegister() throws RemoteException {
		boolean success = service.register(username, password);
		if (success) {
			System.out.println("ע��ɹ�");
		} else {
			System.out.println("ע��ʧ�ܣ�����������");
		}
		return success;
	}

	/**
	 * �û���½��ƥ���û��������롣
	 *
	 * @return ���벻ƥ����û�������ʱ���� false
	 * @throws RemoteException
	 */
	private boolean handleLogin() throws RemoteException {
		boolean success = service.checkUser(username, password);
		if (!success) {
			System.out.println("��½ʧ�ܣ����������û���������");
		} else {
			System.out.println("��½�ɹ�");
		}
		return success;
	}

}
