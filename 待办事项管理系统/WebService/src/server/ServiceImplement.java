package server;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.jws.WebService;

import bean.Matter;
import bean.User;

/**
 * @author 史嘉辉
 * @date 2019-1-4
 */
@WebService
public class ServiceImplement implements Service {

	private int code;
	private static ArrayList<User> users;

	public ServiceImplement() {
		users = new ArrayList<User>();
		/**
		 * 200 成功 201 操作失败 301 事项时间错误 302 事项没有找到 401 用户名已被占用 403 用户确认失败 404 非注册用户
		 * 
		 * 
		 */
		code = 200;
	}

	/**
	 * 验证用户名和密码
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws RemoteException
	 */
	public int checkUser(String userName, String password) {
		int id;
		int length = users.size();
		for (id=0; id < length; id++) {
			String name = users.get(id).getUserName();
			String pwd = users.get(id).getPassword();
			if (name.equals(userName) && pwd.equals(password)) {
				this.code = 200;
				return id;//返回用户的编号
			}
		}
		if (id == length)
			this.code = 403;
		return -1;
	}

	/**
	 * 判断用户名是否被注册过
	 * 
	 * @param user
	 * @return
	 * @throws RemoteException
	 */
	public boolean isRegister(User user) {
		String name = user.getUserName();
		int i;
		for (i = 0; i < users.size(); i++) {
			if (users.get(i).getUserName().equals(name)) {
				this.code = 401;//找到该用户名，已被注册过
				return true;
			}
		}
		if (i == users.size())
			this.code = 200;//該用戶名沒有被注冊過
		return false;
	}

	@Override
	public boolean register(String userName, String password) {

		User user = new User(userName, password, new ArrayList<Matter>());
		if (!isRegister(user)) {
			if (users.add(user)) {
				this.code = 200;
				return true;
			} else {
				this.code = 201;
				return false;
			}
		} else {
			this.code = 401;
		}
		return false;
	}

	@Override
	public boolean addMatter(String description, Date start, Date end, User creator) {
		int uid = checkUser(creator.getUserName(), creator.getPassword());
		if (uid != -1) {
			long Id = new Date().getTime();

			System.out.println(start + "\n" + end);
			if (start.after(end)) {
				this.code = 301;//时间错误
			} else {
				if (users.get(uid).getMatters().add(new Matter(Id, description, start, end))) {
					this.code = 200;//添加成功
					return true;
				} else {
					this.code = 201;//添加失败
				}
			}
		}
		return false;
	}

	@Override
	public ArrayList<Matter> queryMatters(Date start, Date end, User queryer) {
		int uid = checkUser(queryer.getUserName(), queryer.getPassword());
		if (uid != -1) {
			ArrayList<Matter> returnmatters = new ArrayList<Matter>();
			ArrayList<Matter> matters = users.get(uid).getMatters();
			for (int j = 0; j < matters.size(); j++) {
				Date liststart = matters.get(j).getStart();
				Date listend = matters.get(j).getEnd();

				if (liststart.after(start) && end.after(listend)) {
					returnmatters.add(matters.get(j));
				}
			}
			this.code = 200;
			return returnmatters;
		}
		return null;
	}

	@Override
	public boolean deleteMatter(long id, User deleter) {
		int uid = checkUser(deleter.getUserName(), deleter.getPassword());
		if (uid != -1) {
			ArrayList<Matter> matters = users.get(uid).getMatters();
			int length = matters.size();
			int j;
			for (j=0; j < length; j++) {
				if (matters.get(j).getId() == id) {
					if (matters.remove(j) != null) {
						this.code = 200;
						return true;
					} else {
						this.code = 201;
					}
				}
			}
			if (j == length)
				this.code = 302;//事件没有找到
		}
		return false;
	}

	@Override
	public boolean clearMatters(User clearor) {
		int uid = checkUser(clearor.getUserName(), clearor.getPassword());
		if (uid != -1) {
			ArrayList<Matter> matters = users.get(uid).getMatters();
			int length = matters.size();
			for (int j = 0; j < length; j++) {
				matters.remove(j);
			}
		}
		return false;
	}

	@Override
	public int getError() {
		// TODO Auto-generated method stub
		return this.code;
	}

}
