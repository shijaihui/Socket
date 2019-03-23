package bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *  一个用户
 * @author 史嘉辉
 * 
 */
public class User implements Serializable{
	private String name;
	private String password;
	
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String toString(){
		return "[name: "+name+",password:"+password+"]" ;
		
	}
}
