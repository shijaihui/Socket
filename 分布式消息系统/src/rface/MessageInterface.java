package rface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import UserThrow.BadInputException;
import bean.User;
import server.Message;

/**
 *  MessageServer需要实现的接口
 * @author 史嘉辉
 * @date 2018-12-28
 */
public interface MessageInterface extends Remote {
	

	/**
     * 注册用户，注册成功时返回 true，否则（例如用户已经存在）返回 false
     *
     * @param username
     * @param password
     * @return 注册成功时返回 true，否则（例如用户已经存在）返回 false
     * @throws RemoteException
     */
    boolean register(String username, String password) throws RemoteException;
	
    /**
     * 查询所有注册的用户
     *
     * @return 查询到的用户
     * @throws RemoteException
     */
    List<User> showuser()
            throws RemoteException;

    /**
     * 查看用户的所有留言
     * 
     * @return 查询到的留言
     * @throws RemoteException
     */
    List<Message> checkmessages(String username, String password) throws RemoteException;
    
    /**
     * 给其他用户留言
     * 
     * @return 留言成功时返回true
     * @throws RemoteException
     */
    boolean leavemessage(String username, String password, String receiver_user, String message_text) throws RemoteException;
    

    /**
     * 检查用户是否存在且密码正确
     *
     * @param username
     * @param password
     * @return 正确时返回 true，否则返回 false
     * @throws RemoteException
     */
    boolean checkUser(String username, String password)
            throws RemoteException;
    

    /**
     * 检查用户是否存在
     *
     * @param username
     * @return 正确时返回 true，否则返回 false
     * @throws RemoteException
     */
    boolean checkUser(String username)
            throws RemoteException;
}
