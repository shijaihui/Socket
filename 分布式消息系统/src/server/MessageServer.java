package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import UserThrow.BadInputException;
import UserThrow.LoginFailedException;
import bean.User;
import rface.MessageInterface;

/**
 *  服务器的功能实现
 * @author:史嘉辉
 * @date 2018-12-28
 */
public class MessageServer extends UnicastRemoteObject implements MessageInterface {

	private ArrayList<User> users;
	private ArrayList<Message> messages;
	private int messageIds = 0;
	
	Date date = new Date();
	long longTime = date.getTime();

	public MessageServer() throws RemoteException {
		super();
		users = new ArrayList<>();
		messages = new ArrayList<>();
		messageIds = 1;
	}

	@Override
	public boolean leavemessage(String username, String password, String reciever_user, String message_text)
			throws RemoteException {
		Message message = null;
		message.setId(1);
		message.setCreator(username);
		message.setMessage(message_text);
		message.setReciever(reciever_user);
		message.setTime(date);
		if (messages.add(message))
			return true;

		return false;
	}

	@Override
	public List<User> showuser() throws RemoteException {
		return users;

	}

	@Override
	public List<Message> checkmessages(String username, String password) throws RemoteException {
		
		List<Message> mymessage = null;

		for (Message mes : messages) {
			String ex = null;
			ex = mes.getReciever();
			if (ex.equals(null)) {
				System.out.println("此用户没有留言！");
				return mymessage;
			} else {
				if (mes.getReciever().equals(username)) {
					mymessage.add(mes);
				}
				return mymessage;
			}

		}

		return mymessage;

	}

	@Override
	public boolean register(String username, String password) throws RemoteException {
		User user = lookupUser(username);
		if (user != null) {
			return false;
		}

		user = new User(username, password);
		users.add(user);
		return true;
	}

	/**
	 * 进行登陆操作。登入失败时抛出异常。
	 *
	 * @param username
	 * @param password
	 * @throws LoginFailedException
	 */
	private void ensureLogined(String username, String password) throws LoginFailedException {
		if (!login(username, password)) {
			throw new LoginFailedException();
		}
	}

	/**
	 * 登陆。如果用户名和密码存在且正确，则返回true。否则返回false。
	 *
	 * @param username
	 * @param password
	 * @return
	 * @throws RemoteException
	 */
	private boolean login(String username, String password) {
		User user = lookupUser(username);
		return user != null && user.getPassword().equals(password);
	}

	/**
	 * 寻找该用户，找不到则返回 null
	 *
	 * @param username
	 * @return
	 */
	private User lookupUser(String username) {
		for (User item : users) {
			if (item.getName().equals(username)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * meetingID 自动递增
	 *
	 * @return
	 */
	private int generateNewMeetingId() {
		return messageIds++;
	}

	@Override
	public boolean checkUser(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return login(username,password);
	}
	
	@Override
	public boolean checkUser(String username) throws RemoteException {
		// TODO Auto-generated method stub
		User u = null;
		u = lookupUser(username);
		if(u.equals(null)) {
			return false;
		}else {
			return true;
		}
	}
}
