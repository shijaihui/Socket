package bean;

import java.io.Serializable;
import java.util.Date;
/**
 * @author Ê·¼Î»Ô
 */
public class Message implements Serializable, Comparable<Message> {

	private int id;
	private String creator;
	private Date time;
	private String message;
	private String reciever;
	public Message(int id, String creator, Date time, String message,String reciever) {
		this.id = id;
		this.creator = creator;
        this.reciever = reciever;
		this.time = time;
		this.message = message;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}

	private Message() {
		// TODO Auto-generated method stub

	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int compareTo(Message o) {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public int compareTo(Message m) {
//		// TODO Auto-generated method stub
//		 int cmpStart = this.getStart().compareTo(m.getStart());
//	        return cmpStart == 0
//	        				 ? this.getEnd().compareTo(m.getEnd())
//	        				 : cmpStart;
//	}

}
