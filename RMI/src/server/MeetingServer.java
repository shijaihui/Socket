package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import bean.User;
import meetingThrow.AddMeetingException;
import meetingThrow.BadInputException;
import meetingThrow.LoginFailedException;
import rface.MeetingInterface;

/*
 * �������Ĺ���ʵ��
 * @author:ʷ�λ�
 */
public class MeetingServer extends UnicastRemoteObject implements MeetingInterface {

    private ArrayList<User> users;
    private ArrayList<Meeting> meetings;
    private int meetingIds;

    public MeetingServer() throws RemoteException {
        super();
        users = new ArrayList<>();
        meetings = new ArrayList<>();
        meetingIds = 1;
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

    @Override
    public void add(Meeting meeting, String username, String password)
            throws RemoteException, AddMeetingException, LoginFailedException, BadInputException {
        ensureLogined(username, password);

        if (meeting.getStart().after(meeting.getEnd())) {
            throw new BadInputException("�������󣺻���Ľ���ʱ�䲻���ڿ�ʼʱ��֮ǰ��");
        }

        // ������һ�������ʱ���ͻ�Ļ���
        Stream<Meeting> conflictMeetings = meetings.stream()
                .filter(item ->
                        // meeting.start �� item ��ʱ���м�
                        item.getStart().before(meeting.getStart()) && item.getEnd().after(meeting.getStart())
                                // meeting.end �� item ��ʱ���м�
                                || item.getStart().before(meeting.getEnd()) && item.getEnd().after(meeting.getEnd())
                                // item.start �� end �� meeting ��ʱ���м䣨��ֹ meeting �� item ��������ȥ�������
                                || meeting.getStart().before(item.getStart()) && meeting.getEnd().after(item.getStart())
                                || meeting.getStart().before(item.getEnd()) && meeting.getEnd().after(item.getEnd())
                                // item ���õ��� meeting�����˵��غ�ʱ�����ڲ��غϵ�����������ŵ��������鲻�㣩
                                || meeting.getStart().equals(item.getStart()) && meeting.getEnd().equals(item.getEnd())
                );

        // �������Ĳ�ѯʹ��
        ArrayList<String> myMeetingUsers = new ArrayList<>();
        myMeetingUsers.add(meeting.getCreator());
        myMeetingUsers.add(meeting.getOtherUser());

        List<Meeting> conflictMeetingList = conflictMeetings.filter(item ->
                myMeetingUsers.contains(item.getCreator())
                        || myMeetingUsers.contains(item.getOtherUser()))
                .collect(Collectors.toList());

        if (conflictMeetingList.size() >= 1) {
            throw new AddMeetingException(conflictMeetingList);
        }

        meeting.setId(generateNewMeetingId());
        meetings.add(meeting);
    }

    @Override
    public List<Meeting> query(Date start, Date end, String username, String password) throws RemoteException, LoginFailedException {
        ensureLogined(username, password);
        return meetings.stream()
                .filter(item -> !item.getStart().before(start)
                        && !item.getEnd().after(end))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int meetingId, String username, String password) throws RemoteException, LoginFailedException {
        ensureLogined(username, password);
        meetings.removeIf(item -> item.getId() == meetingId && item.getCreator().equals(username));
    }

    @Override
    public void clear(String username, String password) throws RemoteException, LoginFailedException {
        ensureLogined(username, password);
        meetings.removeIf(item -> item.getCreator().equals(username));
    }

    @Override
    public boolean checkUser(String username, String password) throws RemoteException {
        return login(username, password);
    }

    /**
     * ���е�½����������ʧ��ʱ�׳��쳣��
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
     * ��½������û����������������ȷ���򷵻�true�����򷵻�false��
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
     * Ѱ�Ҹ��û����Ҳ����򷵻� null
     *
     * @param username
     * @return
     */
    private User lookupUser(String username) {
        for (User item :
                users) {
            if (item.getName().equals(username)) {
                return item;
            }
        }
        return null;
    }

    /**
     * meetingID �Զ�����
     *
     * @return
     */
    private int generateNewMeetingId() {
        return meetingIds++;
    }
}

