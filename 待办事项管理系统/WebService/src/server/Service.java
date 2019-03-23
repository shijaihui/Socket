package server;


import java.util.ArrayList;
import java.util.Date;

import javax.jws.WebService;

import bean.Matter;
import bean.User;
/**
 * service的接口类
 * @author 史嘉辉
 * @date 2019-1-4
 */

@WebService
public interface Service {

	
	/**
	 * 用户注册
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean register(String userName , String password) ;
	/**
	 * 添加项目
	 * @param description
	 * @param start
	 * @param end
	 * @param creator创建者
	 * @return
	 */
	public boolean addMatter(String description , Date start , Date end , User creator);
	/**
	 * 查询项目
	 * @param start
	 * @param end
	 * @param queryer查询者
	 * @return
	 */
	public ArrayList<Matter> queryMatters(Date start , Date end , User queryer);
	/**
	 * 删除项目
	 * @param id
	 * @param deleter删除者
	 * @return
	 */
	public boolean deleteMatter(long id , User deleter);
	/**
	 * 清空项目
	 * @param clearor
	 * @return
	 */
	public boolean clearMatters(User clearor);
	/**
	 * Interface {@code getError} define the interface required for getting the exception during the runtime
	 * @return
	 */
	public int getError();
	
}

