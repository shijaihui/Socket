package rface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import meetingThrow.AddMeetingException;
import meetingThrow.BadInputException;
import meetingThrow.LoginFailedException;
import server.Meeting;
/*
 * MeetingServer��Ҫʵ�ֵĽӿ�
 * @author ʷ�λ�
 * 2016-12-21
 */
public interface MeetingInterface extends Remote{

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
     * ��ӻ��飬�����ʧ��ʱ��������û������������ͻ���׳��쳣��
     * id�������κ�ֵ��û��Ӱ�졣
     *
     * @param meeting
     * @throws RemoteException
     * @throws AddMeetingException  ��ӻ���ʧ��ʱ�׳����쳣
     * @throws LoginFailedException ��½ʧ��ʱ�׳�
     * @throws BadInputException    Meeting �� start �� end ֮���׳����쳣
     */
    void add(Meeting meeting, String username, String password)
            throws RemoteException, AddMeetingException, LoginFailedException, BadInputException;

    /**
     * ������ֹ��������ѯ����
     *
     * @param start
     * @param end
     * @return ��ѯ���Ļ���
     * @throws RemoteException
     * @throws LoginFailedException ��½ʧ��ʱ�׳�
     */
    List<Meeting> query(Date start, Date end, String username, String password)
            throws RemoteException, LoginFailedException;

    /**
     * ���� id ɾ���û������Ļ��顣��� id �����ڻ�Ϊ�û����У��򲻷����κ��¡�
     *
     * @param meetingId
     * @param username  �û�������������������Ȩ
     * @throws RemoteException
     * @throws LoginFailedException ��½ʧ��ʱ�׳�
     */
    void delete(int meetingId, String username, String password)
            throws RemoteException, LoginFailedException;

    /**
     * ɾ�����û����������л���
     *
     * @param username
     * @throws RemoteException
     * @throws LoginFailedException ��½ʧ��ʱ�׳�
     */
    void clear(String username, String password)
            throws RemoteException, LoginFailedException;

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
}
