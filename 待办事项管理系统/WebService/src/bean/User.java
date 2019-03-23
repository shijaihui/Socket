package bean;

import java.io.Serializable;
import java.util.ArrayList;

import javax.jws.WebService;

/**
 * 用户类
 * @author 史嘉辉
 * @date 2019-1-4
 *
 */
@WebService
public class User implements Serializable{
	
	private String userName;
	private String password;
	private ArrayList<Matter> matters;
	
	/**
	 * 构造函数 
	 * @param userName
	 * @param password
	 * @param matters
	 */
	public User(String userName, String password, ArrayList<Matter> matters) {
		super();
		this.userName = userName;
		this.password = password;
		this.matters = matters;
	}
	/**
	 * 重载构造
	 * @param userName
	 * @param password
	 */
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	/**
	 * 重载构造
	 * @param userName
	 * @param password
	 */
	public User(){
		super();
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Matter> getMatters() {
		return matters;
	}
	public void setMatters(ArrayList<Matter> matters) {
		this.matters = matters;
	}

	
	
	
	
	
}
