package rface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import UserThrow.BadInputException;
import bean.User;
import server.Message;

/**
 *  MessageServer��Ҫʵ�ֵĽӿ�
 * @author ʷ�λ�
 * @date 2018-12-28
 */
public interface MessageInterface extends Remote {
	

	/**
     * ע���û���ע��ɹ�ʱ���� true�����������û��Ѿ����ڣ����� false
     *
     * @param username
     * @param password
     * @return ע��ɹ�ʱ���� true�����������û��Ѿ����ڣ����� false
     * @throws RemoteException
     */
    boolean register(String username, String password) throws RemoteException;
	
    /**
     * ��ѯ����ע����û�
     *
     * @return ��ѯ�����û�
     * @throws RemoteException
     */
    List<User> showuser()
            throws RemoteException;

    /**
     * �鿴�û�����������
     * 
     * @return ��ѯ��������
     * @throws RemoteException
     */
    List<Message> checkmessages(String username, String password) throws RemoteException;
    
    /**
     * �������û�����
     * 
     * @return ���Գɹ�ʱ����true
     * @throws RemoteException
     */
    boolean leavemessage(String username, String password, String receiver_user, String message_text) throws RemoteException;
    

    /**
     * ����û��Ƿ������������ȷ
     *
     * @param username
     * @param password
     * @return ��ȷʱ���� true�����򷵻� false
     * @throws RemoteException
     */
    boolean checkUser(String username, String password)
            throws RemoteException;
    

    /**
     * ����û��Ƿ����
     *
     * @param username
     * @return ��ȷʱ���� true�����򷵻� false
     * @throws RemoteException
     */
    boolean checkUser(String username)
            throws RemoteException;
}
