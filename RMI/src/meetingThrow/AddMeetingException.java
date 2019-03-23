package meetingThrow;

import server.Meeting;

import java.util.List;

/**
 * �������ʧ��
 */
public class AddMeetingException extends Exception {
    private List<Meeting> confilctMeetings;

    public AddMeetingException(List<Meeting> confilctMeetings) {
        this.confilctMeetings = confilctMeetings;
    }

    /**
     * ��
     *
     * @return
     */
    public List<Meeting> getConfilctMeetings() {
        return confilctMeetings;
    }
}
