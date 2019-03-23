package meetingThrow;

import server.Meeting;

import java.util.List;

/**
 * ª·“ÈÃÌº” ß∞‹
 */
public class AddMeetingException extends Exception {
    private List<Meeting> confilctMeetings;

    public AddMeetingException(List<Meeting> confilctMeetings) {
        this.confilctMeetings = confilctMeetings;
    }

    /**
     * ÆÆ
     *
     * @return
     */
    public List<Meeting> getConfilctMeetings() {
        return confilctMeetings;
    }
}
