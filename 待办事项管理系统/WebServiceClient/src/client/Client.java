package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import server.Matter;
import server.ServiceImplement;
import server.ServiceImplementService;
import server.User;

/**
 * 客户端
 * 
 * @author 史嘉辉
 * @date 2019-1-4
 */
public class Client {

	/**
	 * main
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {

		ServiceImplementService ss = new ServiceImplementService();

		ServiceImplement s = ss.getServiceImplementPort();
		Scanner next = new Scanner(System.in);
		printMenu();

		boolean f = true;
		while (f) {
			System.out.print(">>> ");
			String str = next.nextLine();
			String[] ags = str.split(" ");
			switch (ags[0]) {
			case "reg":
				if (ags.length != 3)
					System.out.println("argument error!");
				else
					register(ags, s);
				break;
			case "query":
				if (ags.length != 5)
					System.out.println("argument error!");
				else
					queryMatters(ags, s);
				break;
			case "add":
				if (ags.length != 6)
					System.out.println("argument error!");
				else
					addMatter(ags, s);
				break;
			case "delete":
				if (ags.length != 4)
					System.out.println("argument error!");
				else
					deleteMatter(ags, s);
				break;
			case "clear":
				if (ags.length != 3)
					System.out.println("argument error!");
				else
					clearMatters(ags, s);
				break;
			case "help":
				if (ags.length != 1)
					System.out.println("argument error!");
				else
					printMenu();
				break;
			case "quit":
				if (ags.length != 1)
					System.out.println("argument error!");
				else
					f = false;
				break;
			default:
				System.out.println("请输入菜单中的选项!");
				break;
			}
		}
		System.out.println("Bye!");
	}

	/**
	 * echo menu
	 */
	public static void printMenu() {
		String menu = "MatterService  Menu:\n" + "	1.register\n" + "			reg <username> <password>\n"
				+ "	2.addMatter\n" + "			add <username> <password> <description> <starttime> <endtime>\n"
				+ "	3.queryMatters\n" + "			query <username> <password> <starttime> <endtime>\n"
				+ "	4.deleteMatter\n" + "			delete <username> <password> <matter_id>\n" + "	5.clearMatters\n"
				+ "			clear <username> <password>\n" + "	6.help\n" + "			help \n" + "	7.quit\n"
				+ "			quit\n" + "输入您的选择（例如: reg npu 123456）\n" + "时间的格式为'yy-MM-dd-HH:mm'\n";

		System.out.print(menu);
	}

	/**
	 * 打印状态码的内容
	 * 
	 * @param errorCode
	 */
	public static void printErrorContent(long errorCode) {
		/**
		 * 200 成功 201 操作失败 301 事项时间错误 302 事项没有找到 401 用户名已被占用 403 用户确认失败 404 非注册用户
		 */
		switch ((int) errorCode) {
		case 201:
			System.out.println("operation failed because of error in program");
			break;
		case 404:
			System.out.println("user not register");
			break;
		case 403:
			System.out.println("user confirm failed");
			break;
		case 401:
			System.out.println("user has been registered");
			break;
		case 301:
			System.out.println("Matter Time is illogical");
			break;
		case 302:
			System.out.println("this Matter-id  not found");
			break;
		default:
			break;
		}
	}

	/**
	 * 注册用户
	 * 
	 * @param args
	 * @param ms
	 */
	public static void register(String[] args, ServiceImplement ms) {
		String username = args[1];
		String password = args[2];

		if (ms.register(username, password))
			System.out.println("register successful!");
		else
			printErrorContent(ms.getError());
	}

	/**
	 * 查询事项
	 * 
	 * @param args
	 * @param ms
	 * @throws ParseException
	 */
	public static void queryMatters(String[] args, ServiceImplement ms) throws ParseException {
		SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
		User user = new User();
		user.setUserName(args[1]);
		user.setPassword(args[2]);

		Date start = dateParser.parse(args[3]);
		Date end = dateParser.parse(args[4]);
		ArrayList<Matter> matters = (ArrayList<Matter>) ms.queryMatters(start, end, user);
		System.out.println("发现" + matters.size() + "个待办事项");
		System.out.println("=================================================");
		for (int i = 0; i < matters.size(); i++) {
			System.out.println("事项编号：" + matters.get(i).getId());
			System.out.println("事项描述：" + matters.get(i).getDescription());
			System.out.println("开始时间：" + matters.get(i).getStart());
			System.out.println("结束时间：" + matters.get(i).getEnd());
			System.out.println("-------------------------------------------------");
		}
		System.out.println("=================================================");
	}

	/**
	 * 添加一个事项
	 * 
	 * @param args
	 * @param ms
	 * @throws ParseException
	 */
	public static void addMatter(String[] args, ServiceImplement ms) throws ParseException {

		SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
		User user = new User();
		user.setUserName(args[1]);
		user.setPassword(args[2]);

		if (ms.addMatter(args[3], dateParser.parse(args[4]), dateParser.parse(args[5]), user))
			System.out.println("addMatter successful!");
		else
			printErrorContent(ms.getError());
	}

	/**
	 * 删除一个事项
	 * 
	 * @param args
	 * @param ms
	 */
	public static void deleteMatter(String[] args, ServiceImplement ms) {
		User user = new User();
		user.setUserName(args[1]);
		user.setPassword(args[2]);
		if (ms.deleteMatter(Long.parseLong(args[3]), user))
			System.out.println("deleteMatter successful!");
		else
			printErrorContent(ms.getError());
	}

	/**
	 * 清空事项
	 * 
	 * @param args
	 * @param ms
	 */
	public static void clearMatters(String[] args, ServiceImplement ms) {
		User user = new User();
		user.setUserName(args[1]);
		user.setPassword(args[2]);
		if (ms.clearMatters(user))
			System.out.println("clearMatter successful!");
		else
			printErrorContent(ms.getError());
	}

}
